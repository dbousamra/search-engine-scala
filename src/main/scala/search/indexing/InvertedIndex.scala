package search.indexing

import scala.collection.mutable.LinkedHashMap
import search.documents.Document

class InvertedIndex {

  val index = new LinkedHashMap[String, LinkedHashMap[Document, Int]]
  val weights = new LinkedHashMap[Document, Double]
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
    document.foreach(calculateVectorSpaces(_))
  }

  def calculateVectorSpaces(document: Document) = {
      weights.put(document, vectorWeights(document))
  }

  def similarity(query: Document, document: Document) = {
    dotProduct(query, document) / (vectorWeights(query) * weights.get(document).get)
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

  def dotProduct(query: Document, document: Document) = {
    val queryTfidfs = index.map(word => tfidf(word._1, query))
    val documentTfidfs = index.map(word => tfidf(word._1, document))
    dp(queryTfidfs, documentTfidfs)
  }

  def normalize(word: String, document: Document) = {
    math.sqrt(document.words.foldLeft(0D)((accum, w) => accum + math.pow(idf(w), 2)))
  }

  def tf(word: String, document: Document) = {
    if (document.getWordCount(word) > 0)
      document.getWordCount(word)
    else 0.0
  }

  def idf(word: String) = {
    val occursInAll: Double = index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
    val idf = math.log10(totalDocumentsIndexed / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def tfidf(word: String, document: Document) = tf(word, document) * idf(word)

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