package search.indexing

import search.documents.Document
import search.result.Result
import search.documents.QueryDocument

class SearchRanker[T <: Document](val index: InvertedIndex[T]) {

  def query(inputQuery: QueryDocument): List[Result[T]] = {
    val documents = index.getAllRelevantDocuments(inputQuery.words)
    documents.map(doc => query(inputQuery, doc)).sortBy(_.score).reverse
  }

  def query(query: QueryDocument, document: T): Result[T] = {
      val score = index.similarity(query, document)
      new Result[T](document, score)
  }
}