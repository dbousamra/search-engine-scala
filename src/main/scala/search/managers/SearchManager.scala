package search.managers

import search.indexing.InvertedIndex
import parser.SearchRanker
import java.io.File

class SearchManager {
  
  private val index: InvertedIndex = null
  private val ranker: SearchRanker = null
  private val documentManager = new DocumentManager()
  
  def addFileToIndex(filename: String) = {
    
  }
  
  def addFileToIndex(file: File) = {
    val document = documentManager.parseFile(file)
    // index.addDocumentToIndex(document)
    document
  }

}