package search.documents

import java.io.File
import search.parsing.Parser
import search.parsing.Parser._

class MockDocument(val _name: Option[String], val file: Option[File], words: List[String]) extends Document(words) {

  val name = file match {
    case Some(file) => file.getName()
    case None => _name.getOrElse("Untitled")
  }
  
  override def toString = name
}

class MockDocumentManager {
  
  private val parser = new Parser()
  val removeStopWords: Boolean = true

  def parseFolder(folder: File): List[MockDocument] = {
    folder.listFiles().toList.map(f => parse(f))
  }
  
  def parse(file: File): MockDocument = {
    val words = parser.parse(file, removeStopWords)
    new MockDocument(Some(file.getName()), Some(file), words)
  }
  
  def parseText(input: String, removeStopWords: Boolean = true): MockDocument = {
    val words = parser.parse(input, removeStopWords)
    new MockDocument(None, None, words) 
  }
  
  
}