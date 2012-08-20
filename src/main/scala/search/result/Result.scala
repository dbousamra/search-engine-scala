package search.result

import search.documents.Document
import scala.collection.mutable.Map

case class Result(document: Document, var score: Double, idf: Map[String, Double], tf: Map[String, Double], snippet: Snippet) {
  
  
  def this(document: Document, score: Double, idf: Map[String, Double], tf: Map[String, Double]) = {
    this(document, score, idf, tf, new Snippet(document))
  }
  
  override def toString = "\n" + document + ": Score=" + score + "\n\tIDF's=" + idf + "\n\tTF's =" + tf

}