package search.indexing

import search.documents.Document
import java.lang.Math

class SearchRanker(index: InvertedIndex) {

  def calcQueryScoreCombined(query: List[String]): List[(Document, Double)] = {
    val documents = index.getAllDocuments
    documents.map(d => (d, calcQueryScore(query, d))).sortBy(_._2).reverse
  }
  
  def calcQueryScore(query: List[String], document: Document):Double = {
    query.map{ word =>
      val tfidf = tfTimesIdf(word, document)
      tfidf / normalizeWithDocument(word, document)
      }.reduce(_+_)
  }
  
  def normalizeWithDocument(word: String, document: Document) = {
    math.sqrt(document.words.foldLeft(0D)((accum, w) => accum + math.pow(calcInverseDocumentFrequency(w), 2)))
  }

  def tfTimesIdf(word: String, document: Document) = {
    val tf = calcTermFrequencyInDocument(word, document) 
    val idf = calcInverseDocumentFrequency(word)
          println(document.name + " tf = " + tf + " idf = " + idf )
    tf * idf
  }

  def calcInverseDocumentFrequency(word: String) = {
    val occursInAll:Double = calcTermFrequencyInCorpus(word)
    val documentCount:Double = index.totalDocumentsIndexed
    val idf =  Math.log10(documentCount / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def calcTermFrequencyInDocument(word: String, document: Document) = {
    if (document.getWordCount(word) > 0) {
      document.getWordCount(word).toDouble
    } else 0.0
  }

  def calcTermFrequencyInCorpus(word: String) = {
    index.index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
  }

}