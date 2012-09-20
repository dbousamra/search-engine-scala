package search.documents

import search.parsing.Parser
import search.parsing.Parser._

class QueryDocument(words: List[String]) extends Document(words)

class QueryDocumentManager {
  
  val parser = new Parser

  def parseText(input: String, removeStopWords: Boolean = true): QueryDocument = {
    val words = parser.parse(input, removeStopWords)
    new QueryDocument(words) 
  }
}