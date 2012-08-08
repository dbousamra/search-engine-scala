package search.documents

import java.io.File

class Document(val id: Int, val file: File, val words: List[String]) {

  def getWordCount(word: String) = {
    words.count(x => x.equals(word))
  }

}