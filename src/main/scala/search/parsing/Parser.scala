package search.parsing

import scala.io.Source
import java.io.File
import java.util.Locale

object Parser {
  implicit def file2Iterator(file: File) = {
    Source.fromFile(file, "latin1").getLines()
  }
  implicit def string2Iterator(input: String) = {
    List(input).toIterator
  }
}

class Parser {

  private val STOP_WORDS = "src/resources/stopWords.txt";
  private val stopWords: List[String] = parseStopWords(STOP_WORDS)

  def parseStopWords(path: String) = Source.fromFile(new File(path)).getLines().toList
 
  def parse(input: Iterator[String], removeStopWords: Boolean = true): List[String] = {
    input.map { x =>
      val words = getWordsFromLine(x)
      if (removeStopWords) 
      	filterStopWords(words)
      else
        words
    }.toList.flatten
  }

  private val getWordsFromLine = (line: String) => {
    line.split(" ")
      .map(_.toLowerCase())
      .map(word => word.filter(Character.isLetter(_)))
//      .filter(_.length() > 1)
      .toList
  }

  private val filterStopWords = (words: List[String]) => {
    words.filterNot(word => stopWords.contains(word))
  }
}