package search.result

import search.documents.Document
import scala.collection.mutable.Map

case class Result[T <: Document](document: T, var score: Double, snippet: Snippet) {
  
  
  def this(document: T, score: Double) = {
    this(document, score, new Snippet(document))
  }
  
  override def toString = "\n" + document + ": Score=" + score

}