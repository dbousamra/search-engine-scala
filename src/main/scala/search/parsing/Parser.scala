package search.parsing

import scala.io.Source
import java.io.File
import java.util.Locale

class Parser {

  private val STOP_WORDS = "src/resources/stopWords.txt";
  private val stopWords: List[String] = parseStopWords(STOP_WORDS)

  def parseStopWords(path: String) = Source.fromFile(new File(path)).getLines().toList

  def parse(file: File): List[String] = {
    val lineStream = Source.fromFile(file, "latin1").getLines()
    parse(lineStream)
  }
  
  def parse(input: String): List[String] = {
    parse(List(input).toIterator)
  }
  
  def parse(input: Iterator[String]): List[String] = {
    input.map(x => (getWordsFromLine andThen filterStopWords)(x)).toList.flatten
  }

  private val getWordsFromLine = (line: String) => {
    line.split(" ")
    	.map(_.toLowerCase())
	    .map(word => word.filter(Character.isLetter(_)))
	    .filter(_.length() > 1)
        .toList
  }

  private val filterStopWords = (words: List[String]) => {
    words.filterNot(word => stopWords.contains(word))
  }
}

object Test {
  def main(args: Array[String]) {
    val parser = new Parser()
    val lineStream = parser.parse(new File("src/resources/documents/bible/Exodus.txt"))
    println(lineStream)
  }
}