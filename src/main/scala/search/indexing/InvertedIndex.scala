package search.indexing

import scala.collection.mutable.LinkedHashMap
import search.documents.Document
import search.documents.MockDocument
import search.documents.QueryDocument
import scala.collection.mutable.ArrayBuffer

class InvertedIndex[T <: Document] {

  val index = new LinkedHashMap[String, LinkedHashMap[T, Int]]
  val weights = new LinkedHashMap[T, Double]
  val names = new ArrayBuffer[T]()
  private var _totalDocumentsIndexed = 0

  def addDocumentToIndex(document: T*) = {
    document.foreach { d =>
      d.words.foreach { word =>
        val x = index.getOrElseUpdate(word, LinkedHashMap(d -> 0))
        x.put(d, x.get(d).getOrElse(0) + 1)
      }
      incrementTotalDocumentsIndexed()
    }
    for (doc <- document) {
      calculateVectorSpaces(doc)
      names += (doc)
    }
  }

  def calculateVectorSpaces(document: T) = {
      weights.put(document, vectorWeights(document))
  }

  def similarity(query: QueryDocument, document: T) = {
    dotProduct(query, document) / (vectorWeights(query) * weights.get(document).get)
//    dotProduct(query, document) / (vectorWeights(query) * vectorWeights(document))
  }
  
  def vectorWeights(document: Document) = {
    val weights = index.map { word =>
      math.pow(tfidf(word._1, document), 2)
    }
    math.sqrt(weights.sum)
  }

  /**
   * http://c2.com/cgi/wiki?DotProductInManyProgrammingLanguages
   */
  private def dp[T <% Double](as: Iterable[T], bs: Iterable[T]) = {
    require(as.size == bs.size)
    (for ((a, b) <- as zip bs) yield a * b) sum
  }

  def dotProduct(query: QueryDocument, document: T) = {
    val queryTfidfs = index.map(word => tfidf(word._1, query))
    val documentTfidfs = index.map(word => tfidf(word._1, document))
    dp(queryTfidfs, documentTfidfs)
  }

  def normalize(word: String, document: T) = {
    math.sqrt(document.words.foldLeft(0D)((accum, w) => accum + math.pow(idf(w), 2)))
  }

  def tf(word: String, document: Document) = {
    val count = document.getWordCount(word)
    if (count > 0) count
    else 0.0
  }

  def idf(word: String) = {
    val occursInAll: Double = index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
    val idf = 1.0 + math.log10(totalDocumentsIndexed / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def tfidf(word: String, document: Document) = {
    val tfw = tf(word, document)
    if (tfw == 0) 0 else tfw * idf(word)
  }

  def getAllRelevantDocuments(words: List[String]): List[T] = {
    words.map(word => index.get(word).getOrElse(Nil).map(x => x._1).toList).flatten
  }

  def containsDocument(document: T) = names.contains(document)

  def incrementTotalDocumentsIndexed() = _totalDocumentsIndexed += 1

  def totalDocumentsIndexed = _totalDocumentsIndexed

  override def toString = index.mkString("\n")
}