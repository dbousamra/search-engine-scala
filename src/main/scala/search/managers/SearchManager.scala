package search.managers

import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import java.io.File
import search.documents.Document
import search.parsing.Parser

class SearchManager {
  
  private val _index: InvertedIndex = new InvertedIndex()
  private val ranker: SearchRanker = new SearchRanker(index)
  private val documentManager = new DocumentManager()
  private val parser = new Parser()
  
  def addFileToIndex(filename: String): Document = {
    addFileToIndex(new File(filename))
  }
  
  def addFileToIndex(file: File): Document = {
    val document = documentManager.parseFile(file)
    _index.addDocumentToIndex(document)
    document
  }
  
  def query(input: String) = {
    val queryable = parser.parse(input)
    ranker.calcQueryScoreCombined(queryable)
  }
  
  def index = _index

}