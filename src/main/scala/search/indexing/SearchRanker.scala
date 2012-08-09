package search.indexing

import search.documents.Document
import java.lang.Math

class SearchRanker(index: InvertedIndex) {

  def calcQueryScore(query: List[String], document: Document) = {
    query.map(tfTimesIdf(_, document))
  }

  def tfTimesIdf(word: String, document: Document) = {
    val tf = calcTermFrequencyInDocument(word, document) 
    val idf = calcInverseDocumentFrequency(word)
    tf * idf
  }

  def calcInverseDocumentFrequency(word: String) = {
    val occursInAll = calcTermFrequencyInCorpus(word)
    val documentCount = index.totalDocumentsIndexed
    val idf = Math.log10(documentCount / occursInAll)
    if (idf.isNaN()) 0.0 else idf
  }

  def calcTermFrequencyInDocument(word: String, document: Document) = {
    if (document.getWordCount(word) > 0) {
      document.words.size / document.getWordCount(word)
    } else 0.0
  }

  def calcTermFrequencyInCorpus(word: String) = {
    index.index.get(word) match {
      case Some(occurrence) => occurrence.size
      case None => 0
    }
  }

}