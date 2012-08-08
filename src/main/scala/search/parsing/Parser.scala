package search.parsing

import scala.io.Source
import java.io.File

class Parser {

  private val STOP_WORDS = "src/resources/stopWords.txt";
  private val stopWords = null

  def parseStopWords(path: String) = Source.fromFile(new File(path)).getLines().toList

  def parse(file: File) = {
    val lineStream = Source.fromFile(file).getLines()
//    lineStream.map {
//    
//    }
    lineStream
  }
}

object Test {
  def main(args: Array[String]) {
    val parser = new Parser()
    val lineStream = parser.parse(new File("src/resources/testDocument.txt"))
    println(lineStream.toList)
  }
}