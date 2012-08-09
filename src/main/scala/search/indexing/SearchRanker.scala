package search.indexing

import search.documents.Document
import java.lang.Math

class SearchRanker(index: InvertedIndex) {

  def calcQueryScoreCombined(query: List[String]): List[(Document, Double)] = {
    val documents = index.getAllDocuments
    documents.map(d => (d, calcQueryScore(query, d)))
  }
  
  def calcQueryScore(query: List[String], document: Document):Double = {
    query.map(tfTimesIdf(_, document)).reduce(_+_)
  }

  def tfTimesIdf(word: String, document: Document) = {
    val tf = calcTermFrequencyInDocument(word, document) 
    val idf = calcInverseDocumentFrequency(word)
    val tdidf = tf * idf
    println(tf + " " + idf)
    tdidf
  }

  def calcInverseDocumentFrequency(word: String) = {
    val occursInAll:Double = calcTermFrequencyInCorpus(word)
    val documentCount:Double = index.totalDocumentsIndexed
    println(documentCount + " "  + occursInAll)
    val idf = Math.log10(documentCount / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def calcTermFrequencyInDocument(word: String, document: Document) = {
    if (document.getWordCount(word) > 0) {
      document.words.size.toDouble / document.getWordCount(word).toDouble
    } else 0.0
  }

  def calcTermFrequencyInCorpus(word: String) = {
    index.index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
  }

}