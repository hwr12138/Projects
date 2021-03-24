#include "dectree.h"

// Makefile instruction:
//    To compile:               make
//    To decompress dataset:    make datasets

// Or, to compile manually, use:
//    gcc -o classifier dectree.c classifier.c -g -Wall -std=c99 -lm

// Running decision tree generation / validation:
//    ./classifier datasets/training_data.bin datasets/testing_data.bin

/**
 * main() takes in 2 command line arguments:
 *    - training_data: A binary file containing training image / label data
 *    - testing_data: A binary file containing testing image / label data
 */
int main(int argc, char *argv[]) {
  if (argc != 3) {
    perror("Incorrect number of command arguments\n");
    exit(-1);
  }
  char *trainingData = argv[1];
  char *testingData = argv[2];
  Dataset *trainingSet = load_dataset(trainingData);
  Dataset *testingSet = load_dataset(testingData);
  int total_correct = 0;
  DTNode *root = make_dec_tree(trainingSet);
  for(int i = 0; i < testingSet->num_items; i++) {
    int result = dec_tree_classify(root, &(testingSet->images[i]));
    if (result == testingSet->labels[i]) {
      total_correct++;
    }
  }
  // Print out answer
  printf("%d\n", total_correct);
  free_dec_tree(root);
  free_dataset(trainingSet);
  free_dataset(testingSet);
  return 0;
}