package search.indexing

import scala.collection.mutable.LinkedHashMap
import search.documents.Document

class InvertedIndex {

  val index = new LinkedHashMap[String, LinkedHashMap[Document, Int]]
  private var _totalDocumentsIndexed = 0

  def addDocumentToIndex(document: Document*) = {
    document.foreach { d =>
      d.words.foreach { word =>
        index.get(word) match {
          case Some(x) => index.get(word).get.put(d, x.get(d).getOrElse(0) + 1)
          case None => {
            index.getOrElseUpdate(word, LinkedHashMap(d -> 1))
          }
        }
      }
      incrementTotalDocumentsIndexed()
    }
  }
  
  def getAllRelevantDocuments(words: List[String]) = {
    words.map(word => index.get(word).get.map(x => x._1).toList).flatten
  }

  def getAllDocuments = {
    index.map(x => x._2.keys).flatten.toList.distinct
  }

  def incrementTotalDocumentsIndexed() = _totalDocumentsIndexed += 1

  def totalDocumentsIndexed = _totalDocumentsIndexed
  
  override def toString = index.mkString("\n")
}