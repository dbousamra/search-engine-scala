package search.indexing

import scala.collection.mutable.LinkedHashMap

import search.documents.Document
import search.managers.DocumentManager
import search.result.Result

class SearchRanker(val index: InvertedIndex) {

  def query(inputQuery: Document): List[Result] = {
    val documents = index.getAllRelevantDocuments(inputQuery.words)
    println(documents.length)
    documents.map(doc => query(inputQuery, doc)).sortBy(_.score).reverse
  }

  def query(query: Document, document: Document): Result = {
    val score = index.similarity(query, document)
    new Result(document, score)
  }
}