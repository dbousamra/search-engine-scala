package search.documents

import java.io.File

class Document(val id: Int, val name: Option[String], val file: Option[File], val words: List[String]) {

  def getWordCount(word: String) = {
    words.count(x => x.equals(word))
  }

  override def toString = name.getOrElse("Untitled document")
}