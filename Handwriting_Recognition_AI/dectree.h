#pragma once

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * For the recursive call with M images, we want to terminate recursion and 
 *   create a leaf node if the most frequent among the M labels has a ratio
 *   of at least this much. In other words, we terminate if:
 * 
 *      (freq of most common label) / M   >=  TERMINATE_RATIO
 */
#define TERMINATE_RATIO 0.95

/* This struct stores the data for an image */
typedef struct {
    int sx;               // x resolution
    int sy;               // y resolution
    unsigned char *data;  // List of `sx * sy` pixel color values [0-255]
} Image;

/* This struct stores the images / labels in the dataset */
typedef struct {
    int num_items;          // Number of images in the dataset
    Image *images;          // List of `num_items` Image structs
    unsigned char *labels;  // List of `num_items` labels [0-9]
} Dataset;

/* The following struct represents a node in the decision tree. */
typedef struct dt_node {
    int pixel;              // Which pixel to check in this node
    int classification;     // (Leaf nodes) Classification for this node
    struct dt_node *left;   // Left child   (color at `pixel` == 0)  
    struct dt_node *right;  // Right child  (color at `pixel` == 255)
} DTNode;

Dataset *load_dataset(const char *filename);

void get_most_frequent(Dataset *data, int M, int indices[M], int *label, int *freq);
int find_best_split(Dataset *data, int M, int indices[M]);

DTNode *make_dec_tree(Dataset *data);
int dec_tree_classify(DTNode *root, Image *img);

void free_dataset(Dataset *data);
void free_dec_tree(DTNode *root);
