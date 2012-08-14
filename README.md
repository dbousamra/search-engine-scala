Scala Inverted Index
====================

A very naive/simple implementation of an inverted index with simple query interface.


## Features
* Inverted index stores a word -> document occurence. At the moment it's just the occurence and the count per term. 

```scala
val searchManager = new SearchManager
searchManager.addFolderToIndex(new File("src/resources/documents/mopp"))
```

* Simple query interface returning `List[Document, Double]`:

```scala
val queryResult = searchManager.query("supplementary")
println(queryResult)
```

gives:

```scala
List(
(E_06_04.txt,0.1459099116015455), 
(E_06_01.txt,0.02065555950244101), 
(C_05_02.txt,0.010876058416236544), 
(B_09_07.txt,0.007757806737392836), 
(I_07_03.txt,0.00354470406715619), 
(E_06_05.txt,0.0034425932504068345), 
(E_09_01.txt,0.0031970069803141177), 
(C_05_01.txt,0.0023087860897392665), 
(C_04_07.txt,0.0017636335063574017)
)
```

which compares favorably with `grep supplementary . -R -l -i`

```unix
./B_09_07.txt
./C_04_07.txt
./C_05_01.txt
./C_05_02.txt
./E_06_01.txt
./E_06_04.txt
./E_06_05.txt
./E_09_01.txt
./I_07_03.txt
```

## What next?

* Distance between words to match phrases
* Implement a safe concurrent hashmap. Not threadsafe at the moment
* Add map reduce functionality using Akka actors perhaps?
* A frontend to view queries
* Snippets. Return the phrase the query is included from.