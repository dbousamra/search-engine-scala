package search.indexing

import scala.collection.mutable.LinkedHashMap
import search.documents.Document

class InvertedIndex {

  val index = new LinkedHashMap[String, LinkedHashMap[Document, Int]]
  private var _totalDocumentsIndexed = 0

  def addDocumentToIndex(document: Document) = {
    document.words.foreach { word =>
      index.get(word) match {
        case Some(x) => index.get(word).get.put(document, x.get(document).getOrElse(0) + 1)
        case None => {
          index.put(word, new LinkedHashMap[Document, Int])
          index.get(word).get.put(document, 1)
        }
      }
    }
    incrementTotalDocumentsIndexed()
  }
  
  def getAllDocuments = {
    index.map(x => x._2.keys).flatten.toList.distinct
  }

  def incrementTotalDocumentsIndexed() = _totalDocumentsIndexed += 1

  def totalDocumentsIndexed = _totalDocumentsIndexed
}