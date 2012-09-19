package search.parsing

import scala.io.Source
import java.io.File
import java.util.Locale
import java.io.InputStream

object Parser {
  implicit def file2Iterator(file: File) = {
    Source.fromFile(file, "latin1").getLines()
  }
  implicit def string2Iterator(input: String) = {
    List(input).toIterator
  }
}

class Parser {

  private val STOP_WORDS = "search/parsing/stopWords.txt";
  private val stopWords: Set[String] = parseStopWords(getClass.getClassLoader.getResourceAsStream(STOP_WORDS))

  def parseStopWords(stream: InputStream) = Source.fromInputStream(stream).getLines().toSet
 
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