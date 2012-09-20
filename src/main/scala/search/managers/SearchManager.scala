package search.managers

import scala.Array.canBuildFrom
import search.documents.Document
import search.documents.QueryDocumentManager
import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import search.parsing.Parser
import search.parsing.Parser.string2Iterator

class SearchManager[T <: Document] {

  private val _index: InvertedIndex[T] = new InvertedIndex()
  private val ranker: SearchRanker[T] = new SearchRanker(index)
  private val parser: Parser = new Parser()

  def addToIndex(documents: Traversable[T]): List[T] = {
    documents.map(addToIndex).toList
  }
  
  def addToIndex(document: T): T = {
    doesDocumentAlreadyExist(document) match {
      case Some(docFound) => docFound
      case None => {
        _index.addDocumentToIndex(document)
        document
      }
    }
  }

  def doesDocumentAlreadyExist(document: T): Option[T] = {
    index.containsDocument(document)
  }

  def query(input: String) = {
    val queryable = new QueryDocumentManager().parseText(input)
    ranker.query(queryable).filter(d => d.score > 0.0)
  }
  
  def index = _index

}