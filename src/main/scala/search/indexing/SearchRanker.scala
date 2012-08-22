package search.indexing

import scala.collection.mutable.LinkedHashMap

import search.documents.Document
import search.managers.DocumentManager
import search.result.Result

class SearchRanker(index: InvertedIndex) {

  def query(inputQuery: Document): List[Result] = {
    val documents = index.getAllRelevantDocuments(inputQuery.words)
    println(documents)
    documents.map(doc => query(doc, inputQuery)).sortBy(_.score).reverse
  }

  def query(query: Document, document: Document): Result = {
    val score = similarity(query, document)
    new Result(document, score)
  }

  def similarity(query: Document, document: Document) = {
    dotProduct(query, document) / (vectorWeights(query) * vectorWeights(document))
  }

  /**
   * http://c2.com/cgi/wiki?DotProductInManyProgrammingLanguages
   */
  private def dp[T <% Double](as: Iterable[T], bs: Iterable[T]) = {
    require(as.size == bs.size)
    (for ((a, b) <- as zip bs) yield a * b) sum
  }

  def dotProduct(query: Document, document: Document) = {
    val queryTfidfs = index.index.map(word => tfidf(word._1, query))
    val documentTfidfs = index.index.map(word => tfidf(word._1, document))
    dp(queryTfidfs, documentTfidfs)
  }

  def vectorWeights(document: Document) = {
    math.sqrt(index.index.map { word =>
      math.pow(tfidf(word._1, document), 2)
    }.sum)
  }

  def normalize(word: String, document: Document) = {
    math.sqrt(document.words.foldLeft(0D)((accum, w) => accum + math.pow(idf(w), 2)))
  }

  def tf(word: String, document: Document) = {
    if (document.getWordCount(word) > 0)
      //      document.getWordCount(word).toDouble / document.words.size.toDouble
      document.getWordCount(word)
    else 0.0
  }

  def idf(word: String) = {
    val occursInAll: Double = index.index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
    val idf = math.log10(index.totalDocumentsIndexed / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def tfidf(word: String, document: Document) = tf(word, document) * idf(word)
}