#include "dectree.h"

/**
 * This function takes in the name of the binary file containing the data and
 * loads it into memory. The binary file format consists of the following:
 *
 *     -   4 bytes : `N`: Number of images / labels in the file
 *     -   1 byte  : Image 1 label
 *     - 784 bytes : Image 1 data (28x28)
 *          ...
 *     -   1 byte  : Image N label
 *     - 784 bytes : Image N data (28x28)
 */
Dataset *load_dataset(const char *filename) {
  // Allocate data, read image data / labels, return
  FILE *file = fopen(filename, "rb");
  if (file == NULL) {
    fprintf(stderr, "error opening file\n");
    return NULL;
  }
  Dataset *set = malloc(sizeof(Dataset));
  int numImg;
  fread(&numImg, sizeof(int), 1, file);
  Image *images = malloc(sizeof(Image) * numImg);
  unsigned char *labels = malloc(sizeof(unsigned char) * numImg);
  set->images = images;
  set->labels = labels;
  set->num_items = numImg;
  for(int k = 0; k < numImg; k++) {
    unsigned char *data = malloc(sizeof(unsigned char) * 784);
    set->images[k].sx = 28;
    set->images[k].sy = 28;
    set->images[k].data = data;
    for(int i = 0; i < 785; i++) {
      if (i == 0) {
        // scan label
        fread(&(set->labels[k]), sizeof(unsigned char), 1, file);
      } else if (i >= 0) {
        // scan Img data
        fread(&(set->images[k].data[i]), sizeof(unsigned char), 1, file);
      }
    }
  }
  return set;
}

/**
 * This function computes and returns the Gini impurity of the M images
 * (with their indices stored in the array) if they were to be split
 * based on the given pixel.
 */
double split_gini_impurity(Dataset *data, int M, int indices[M], int pixel) {
  int a_freq[10] = {0}, a_count = 0;
  int b_freq[10] = {0}, b_count = 0;
  for (int i = 0; i < M; i++) {
    int img_idx = indices[i];
    // The pixels are always either 0 or 255, but using < 128 for generality.
    if (data->images[img_idx].data[pixel] < 128)
      a_freq[data->labels[img_idx]]++, a_count++;
    else
      b_freq[data->labels[img_idx]]++, b_count++;
  }

  double a_gini = 0, b_gini = 0;
  for (int i = 0; i < 10; i++) {
    double a_i = ((double)a_freq[i]) / ((double)a_count);
    double b_i = ((double)b_freq[i]) / ((double)b_count);
    if (a_count) a_gini += a_i * (1 - a_i);
    if (b_count) b_gini += b_i * (1 - b_i);
  }
  // Weighted average of gini impurity of children
  return (a_gini * a_count + b_gini * b_count) / M;
}

/**
 * Given a subset of M images and their corresponding indices, find and return
 * the most frequent label and it's frequency. The last 2 parameters are
 * only for outputs.
 *
 * - The most frequent label should be stored in `*label`
 * - The frequency of this label should be stored in `*freq`
 * 
 * If multiple labels have the same maximal frequency, return the smallest one.
 */
void get_most_frequent(Dataset *data, int M, int indices[M], 
                       int *label, int *freq) {
  int label_occurence[10] = {};
  for (int i = 0; i < M; i++) {
    label_occurence[(int)(data->labels[indices[i]])]++;
  }
  int greatest_label = 0, greatest_freq = 0;
  for (int i = 0; i < 10; i++) {
    if (label_occurence[i] > greatest_freq) {
      greatest_freq = label_occurence[i];
      greatest_label = i;
    }
  }
  *label = greatest_label;
  *freq = greatest_freq;
  return;
}

/**
 * Given a subset of M images and their corresponding indices, find and return
 * the best pixel to split the data based on. The best pixel is the one which
 * has the minimum Gini impurity as computed by `split_gini_impurity()`.
 * 
 * The return value should be a number between 0-783 (inclusive), representing
 *  the pixel the M images should be split based on.
 * 
 * If multiple pixels have the same minimal Gini impurity, return the smallest.
 */
int find_best_split(Dataset *data, int M, int indices[M]) {
  double pixel_impurity[784] = {};
  for(int i = 0; i < 784; i++) {
    pixel_impurity[i] = split_gini_impurity(data, M, indices, i);
  }
  int min_gini_pixel = 0;
  for (int i = 0; i < 784; i++) {
    if (pixel_impurity[i] < pixel_impurity[min_gini_pixel]) {
      min_gini_pixel = i;
    }
  }
  return min_gini_pixel;
}

/**
 * This is the recursive function that creates the Decision tree. In each
 * recursive call, we only want to consider some subset of the entire dataset
 * corresponding to the node. To represent this, we pass in an array of indices
 * of these images in the dataset, along with it's length M. Be careful to
 * allocate this on the array for any recursive calls made, and free it when
 * you're done with creating the tree. Here, you need to:
 *
 *    - Compute ratio of most frequent image in current call, terminate if >95%
 *    - Find the best pixel to split on using `split_gini_impurity()`
 *    - Split the data, allocate corresponding arrays, and recurse
 *    - In either case, allocate a new node, set the correct values and return
 *       - If it's a child, set `classification`, and both children = NULL.
 *       - If it's not a child, set `pixel` and `left`/`right` (recursively). 
 */
DTNode *make_helper(Dataset *data, int M, int indices[M]) {
  DTNode *node = malloc(sizeof(DTNode));
  node->pixel = -1;
  node->classification = -1;
  node->left = NULL;
  node->right = NULL;
  int label, freq, best_pixel, left_size = 0, right_size;
  get_most_frequent(data, M, indices, &label, &freq);
  float ratio = (float)freq / (float)M;
  if(ratio > TERMINATE_RATIO) {
    node->classification = label;
    return node;
  }
  best_pixel = find_best_split(data, M, indices);
  node->pixel = best_pixel;
  for(int i = 0; i < M; i++) {
    if(data->images[indices[i]].data[best_pixel] == (unsigned char)0) {
      left_size++;
    }
  }
  right_size = M - left_size;
  int left_indices[left_size], right_indices[right_size], leftCount = 0, rightCount = 0;
  for(int i = 0; i < M; i++) {
    if(data->images[indices[i]].data[best_pixel] == (unsigned char)0) {
      left_indices[leftCount] = indices[i];
      leftCount++;
    } else {
      right_indices[rightCount] = indices[i];
      rightCount++;
    }
  }
  node->left = make_helper(data, leftCount, left_indices);
  node->right = make_helper(data, rightCount, right_indices);
  return node;
}

/**
 * This is the function exposed to the user. 
 */
DTNode *make_dec_tree(Dataset *data) {
  int M = data->num_items;
  int indices[M];
  for(int i = 0; i < M; i++) {
    indices[i] = i;
  }
  DTNode *node = make_helper(data, M, indices);
  return node;
}

/**
 * Given a decision tree and an image to classify, return the predicted label.
 */
int dec_tree_classify(DTNode *root, Image *img) {
  DTNode *current = root;
  while(current->classification == -1) {
    if (img->data[current->pixel] == (unsigned char)0) {
      current = current->left;
    } else {
      current = current->right;
    }
  }
  int result = current->classification;
  return result;
}

/**
 * This function frees the Decision tree.
 */
void free_dec_tree(DTNode *node) {
  if (node ==  NULL) {
    return;
  }
  if (node->left != NULL) {
    free_dec_tree(node->left);
  } else if (node->right != NULL) {
    free_dec_tree(node->right);
  }
  free(node);
  return;
}

/**
 * Free all the allocated memory for the dataset
 */
void free_dataset(Dataset *data) {
  for(int i = 0; i < data->num_items; i++) {
    free(data->images[i].data);
  }
  free(data->images);
  free(data->labels);
  free(data);
  return;
}