package search.managers

import java.io.File
import search.parsing.Parser
import search.documents.Document
import scala.io.Source
import search.parsing.Parser._

class DocumentManager {
  
  private var _documentCount = 0
  private val parser = new Parser()

  def parseFile(file: File, removeStopWords: Boolean = true): Document = {
    val words = parser.parse(file, removeStopWords)
    incDocumentCount()
    new Document(documentCount, Some(file.getName()), Some(file), words)
  }
  
  def parseText(input: String, removeStopWords: Boolean = true): Document = {
    val words = parser.parse(input, removeStopWords)
    incDocumentCount();
    new Document(documentCount, None, None, words)
  }
  
  private def incDocumentCount() = _documentCount += 1
  
  def documentCount = _documentCount
}