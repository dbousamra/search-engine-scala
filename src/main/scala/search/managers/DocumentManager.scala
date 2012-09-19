package search.managers

import java.io.File
import search.parsing.Parser
import search.documents.Document
import scala.io.Source
import search.parsing.Parser._

class DocumentManager {
  
  private val parser = new Parser()

  def parseFile(file: File, removeStopWords: Boolean = true): Document = {
    val words = parser.parse(file, removeStopWords)
    new Document(Some(file.getName()), Some(file), words)
  }
  
  def parseText(input: String, removeStopWords: Boolean = true): Document = {
    val words = parser.parse(input, removeStopWords)
    new Document(None, None, words)
  }
  
  
}