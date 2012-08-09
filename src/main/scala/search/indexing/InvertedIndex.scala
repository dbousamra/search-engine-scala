package search.indexing

import scala.collection.mutable.LinkedHashMap
import search.documents.Document

class InvertedIndex {

  val index = new LinkedHashMap[String, LinkedHashMap[Int, Int]]
  private var _totalDocumentsIndexed = 0

  def addDocumentToIndex(document: Document) = {
    document.words.foreach { word =>
      index.get(word) match {
        case Some(x) => index.get(word).get.put(document.id, x.get(document.id).getOrElse(1))
        case None => {
          index.put(word, new LinkedHashMap[Int, Int])
          index.get(word).get.put(document.id, 1)
        }
      }
    }
    incrementTotalDocumentsIndexed()
  }

  def incrementTotalDocumentsIndexed() = _totalDocumentsIndexed += 1

  def totalDocumentsIndexed = _totalDocumentsIndexed

}