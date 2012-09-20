package search.documents

abstract class Document(val words: List[String]) {

  def getWordCount(word: String) = counts.getOrElse(word, 0)

  private lazy val counts = words.foldLeft(collection.mutable.HashMap[String, Int]()) {
    (map, word) => map += word -> (map.getOrElse(word, 0) + 1)
  }

}