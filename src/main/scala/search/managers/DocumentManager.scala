package search.managers

import java.io.File

class DocumentManager {
  
  var documentCount = 0
  val parser = null

  def parseFile(file: File) = {
    val words = null
    incDocumentCount()
  }
  
  private def incDocumentCount() = documentCount + 1
  
}