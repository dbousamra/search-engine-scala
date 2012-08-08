package search.documents

import java.io.File

class Document(id: Int, file: File, words: List[String]) {

  def getWordCount(word: String) = {
    words.count(x => x.equals(word))
  }
  
}