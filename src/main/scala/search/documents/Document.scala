package search.documents

import java.io.File

class Document(val id: Int, val _name: Option[String], val file: Option[File], val words: List[String]) {

  def getWordCount(word: String) = counts.getOrElse(word, 0)

  private lazy val counts = words.foldLeft(collection.mutable.HashMap[String, Int]()) {
    (map, word) => map += word -> (map.getOrElse(word, 0) + 1)
  }

  val name = file match {
    case Some(file) => file.getName()
    case None => _name.getOrElse("Untitled")
  }
  
  override def toString = name
}