package search.managers

import java.io.File
import search.parsing.Parser
import search.documents.Document

class DocumentManager {
  
  private var _documentCount = 0
  private val parser = new Parser()

  def parseFile(file: File): Document = {
    val words = parser.parse(file)
    incDocumentCount()
    new Document(documentCount, file, words)
  }
  
  private def incDocumentCount() = documentCount + 1
  
  def documentCount = _documentCount
}