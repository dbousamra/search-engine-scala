Scala Inverted Index
====================

A very naive/simple implementation of an inverted index with simple query interface.


## Features
* Inverted index stores a word -> document occurence. At the moment it's just the occurence and the count per term. 

* Simple query interface:

    val searchManager = new SearchManager
    searchManager.addFolderToIndex(new File("src/resources/documents/"))
    searchManager
    

sdf