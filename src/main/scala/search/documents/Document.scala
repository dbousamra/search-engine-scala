package search.documents

import java.io.File

class Document(val id: Int, val _name: Option[String], val file: Option[File], val words: List[String]) {

  def getWordCount(word: String) = {
    words.count(x => x.equals(word))
  }

  val name = file match {
    case Some(file) => file.getName()
    case None => _name.getOrElse("Untitled")
  }
  
  override def toString = name
}