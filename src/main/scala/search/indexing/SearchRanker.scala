package search.indexing

import search.documents.Document
import scala.collection.SortedMap
import scala.collection.mutable.LinkedHashMap
import search.result.Result
import scala.collection.mutable.HashMap

class SearchRanker(index: InvertedIndex) {

  def query(input: List[String]): List[Result] = {
    val documents = index.getAllDocuments
    documents.map(d => query(input, d)).sortBy(_.score).reverse
  }

  def query(input: List[String], document: Document): Result = {
    input.foldLeft(new Result(document, 0.0, new HashMap[String, Double](), new HashMap[String, Double]())) { (result, word) =>
      val tfval = tf(word, document)
      val idfval = idf(word)
      val normalized = tfval * idfval
      result.idf += word -> idfval
      result.tf += word -> tfval
      result.score = normalized
      result
    }
  }

  val normalize = (word: String, document: Document) => {
    math.sqrt(document.words.foldLeft(0D)((accum, w) => accum + math.pow(idf(w), 2)))
  }
  
  val tf = (word: String, document: Document) => {
    if (document.getWordCount(word) > 0)
      document.getWordCount(word).toDouble / document.words.size.toDouble
    else 0.0
  }

  val idf = (word: String) => {
    val occursInAll: Double = index.index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
    val idf = 1 + math.log10(index.totalDocumentsIndexed / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }
}