package search.documents

import java.io.File

class Document(val id: Int, val name: String, val file: File, val words: List[String]) {

  def getWordCount(word: String) = {
    words.count(x => x.equals(word))
  }

  //override def toString = "[" + id + "] " +  file.getName() + " - " + words.length + " words"
  override def toString = file.getName()
}