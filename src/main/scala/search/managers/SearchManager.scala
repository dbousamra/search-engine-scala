package search.managers

import scala.Array.canBuildFrom
import search.documents.Document
import search.documents.QueryDocumentManager
import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import search.parsing.Parser
import search.parsing.Parser.string2Iterator

class SearchManager[T <: Document] {

  private val _index = new InvertedIndex[T]()
  private val ranker = new SearchRanker[T](index)
  private val parser: Parser = new Parser()

  def addToIndex(documents: Traversable[T]): List[T] = {
    documents.map(addToIndex).toList
  }
  
  def addToIndex(document: T): T = {
    if (index.containsDocument(document)) {
      document
    } else {
      _index.addDocumentToIndex(document)	
      document
    }
  }

  def query(input: String) = {
    val queryable = new QueryDocumentManager().parseText(input)
    ranker.query(queryable).filter(d => d.score > 0.0).take(100)
  }

  def queryMatch(input: String) = {
    val queryable = new QueryDocumentManager().parseText(input)
//    _index.index.keys.filter(_.startsWith(input).)
    val x = _index.index.filter(_._1.startsWith(input))
  }
  
  def index = _index

}