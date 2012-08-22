package search.result

import search.documents.Document
import scala.collection.mutable.Map

case class Result(document: Document, var score: Double, snippet: Snippet) {
  
  
  def this(document: Document, score: Double) = {
    this(document, score, new Snippet(document))
  }
  
  override def toString = "\n" + document + ": Score=" + score

}