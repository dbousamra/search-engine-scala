package search.managers

import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import java.io.File
import search.documents.Document

class SearchManager {
  
  private val index: InvertedIndex = new InvertedIndex()
  private val ranker: SearchRanker = new SearchRanker(index)
  private val documentManager = new DocumentManager()
  
  def addFileToIndex(filename: String): Document = {
    addFileToIndex(new File(filename))
  }
  
  def addFileToIndex(file: File): Document = {
    val document = documentManager.parseFile(file)
    index.addDocumentToIndex(document)
    document
  }

}