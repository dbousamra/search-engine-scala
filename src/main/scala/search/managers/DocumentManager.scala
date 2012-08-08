package search.managers

import java.io.File
import search.parsing.Parser

class DocumentManager {
  
  var documentCount = 0
  private val parser = new Parser()

  def parseFile(file: File): List[String] = {
    incDocumentCount()
    parser.parse(file)
  }
  
  private def incDocumentCount() = documentCount + 1
  
}