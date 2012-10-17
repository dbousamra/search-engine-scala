#Project Motivation:

My motivation for this project surrounded the understanding and implementation of a search engine. I wanted to implement the ideas and techniques we learnt in the first 5 lectures. Specifically, inverted indexes, cosine similarity ranking algorithms and natural language processing of queries. I wanted to implement a fully working search library that would be comparable in accuracy and speed to Lucene, all without referencing/looking at Lucene in development. 

#Background Information:

Recently the National Archives of Australia open sourced their database of images. I wanted to take advantage of this data (380,000 documents and images with basic metadata) and develop a basic search application using my search library. 

#Architecture and Implementation Details:

My project is split up into two arbitrary development items:

##Search engine library:

My search library consists of around 1200 lines of Scala code with associating tests across 18 classes. The library can be divided up into several parts:

####Documents:
The concept of a document relates to the input of an arbitrary item to be indexed, ranked and searched upon. This package consists of a abstract class that defines the basic necessary attributes of a document. It consists of just two necessary field: counts, the contents of the documents as a list of words, and a word count.
```scala
abstract class Document(val words: List[String]) {
  def getWordCount(word: String)
}
```

From this base Document we can define other, more specific documents, so long as they implement the base Document. For instance, during testing I chose to use both the Bible and QUT’s MOPP document database as test cases. This document type can be represented as a base Document with a filename and file location. For the National Archives of Australia I used a more sophisticated document that had more attributes.

```scala
class NationalArchiveDocument(
    val barcode: String, 
    title: List[String],
    val description: String,
    val year: Int, 
    val location: String, 
    val largeImageURL: String, 
    val smallImageURL: String) extends Document(title)
```

Along with defining a document, a document “parser” must also be defined. I wanted the search library to be as orthogonal and generic as possible, allowing the search of almost ANY type of document, no matter how it is structured (so long as it consisted of some text). This class defines a method of parsing some text into a collection of documents. For instance, the QUT MOPP corpus is just a collection of text documents with a filename, so it’s accompanying document parser class is quite small. Whereas the National Archives documents are contained within a single CSV file, so a substantially more complex parser was defined:

```scala
class NationalArchiveDocumentManager {
  // the generic word parser that stops words etc
  private val parser = new Parser()

  // a public parser method that takes in the NationalArchives CSV document 
  // from their website, and returns a parsed collection of NationalArchiveDocuments
  def parse(filename: String): Seq[NationalArchiveDocument] = {
    val reader = new CSVReader(new FileReader(filename));    
    val iterator = Iterator.continually(reader.readNext()).takeWhile(_ != null)
    iterator.toSeq.tail.map(parseRow)
  }

  // takes a single row in the CSV and returns a NationalArchiveDocument
  def parseRow(row: Array[String]): NationalArchiveDocument = {
    new NationalArchiveDocument(
      barcode = row(0),
      description = row(1),
      title = parser.parse(row(1)),
      year = row(2).toInt,
      location = row(3),
      largeImageURL = row(4),
      smallImageURL = row(5))
  }
}
```

####Indexing and ranking:

####Search managers:

####Parsing:

####Results:



##National Archives of Australia search engine:

