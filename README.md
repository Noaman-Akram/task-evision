# task-evision
####  By Noeman Akram 


My understanding and approach


### requirements : 

Only alphanumeric 
Orders of words are not important 
scoring range (%): 0 - 100

1 - 20 files
1 - 10M words /file

### Solution proposal :

1- Read file A and pool Folder (Assumption: they are not on same path)
2- Read all files
3- score similarity using an algorithm that assesses the similarity between the contents of A.txt and each file in the pool
4- Show the most matching file

: 

used expressions to filter out non-alphanumeric characters when extracting words.
then, Used sets to store words
used Jaccard to measure similarity, which operates on sets of words disregarding the order of words
The similarity score is computed as a ratio of the intersection of word sets to their union, then scaled to a percentage

Focused on improving the reading of large files so, 
Used Files.lines(p) to handle large text files (up to 10 million words) efficiently  which allows the file to be read line-by-line, instead of loading the entire content into memory

No need for parallel file processing as problem statement specifies that the pool maximum can contain up to 20 files only.

 
