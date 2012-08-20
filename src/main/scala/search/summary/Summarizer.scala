package search.summary

import java.text.BreakIterator
import java.util.Locale
import scala.collection.JavaConversions._
import search.indexing.InvertedIndex
import search.indexing.SearchRanker
import search.managers.DocumentManager
import search.parsing.Parser
import scala.io.Source
import java.io.File
import java.util.Formatter

class Summarizer(source: String) {

  val parser = new Parser
  val documentManager = new DocumentManager

  def summarize() = {
    val index = new InvertedIndex
    sentences.foreach { s =>
      val document = documentManager.parseText(s)
      index.addDocumentToIndex(document)
    }
    //    println("INDEX = " + index.index.mkString("\n"))
    val searchRanker = new SearchRanker(index)
    val x = sentences.map { sentence =>
      val words = parser.parse(sentence)
      val score = words.map { word => 
        val document = index.index.get(word)
        val idf = searchRanker.idf(word)
        idf
      }.foldLeft(0.0)((r, a) => a + r)
      println(words + " " + score)
      (score, sentence)
    }

    println(x.sortBy(_._1).mkString("\n"))

  }

  val sentences = {
    val wordIterator = BreakIterator.getSentenceInstance(Locale.US);
    wordIterator.setText(source);
    Iterator.iterate(wordIterator.first)(_ => wordIterator.next)
      .takeWhile(_ != BreakIterator.DONE)
      .sliding(2)
      .map {
        case List(start, end) => source.substring(start, end).trim
      }
      .toList
  }
}

object Summarizer {
  def main(args: Array[String]) = {
    val input = Source.fromFile(new File("src/resources/documents/bible/Genesis.txt"), "latin1").getLines.mkString
    val summarizer = new Summarizer(input).summarize
    //println(summarizer.sortBy(_._2).mkString("\n"))

  }
}