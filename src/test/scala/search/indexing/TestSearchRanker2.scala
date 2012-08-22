package search.indexing

import org.junit.Assert._
import org.junit.Test
import search.managers.DocumentManager
import org.junit.Before

class TestSearchRanker2 {

  //based on http://www.miislita.com/term-vector/term-vector-3.html

  val documentManager = new DocumentManager
  val query = documentManager.parseText("gold silver truck", false)
  val doc1 = documentManager.parseText("Shipment of gold damaged in a fire", false)
  val doc2 = documentManager.parseText("Delivery of silver arrived in a silver truck", false)
  val doc3 = documentManager.parseText("Shipment of gold arrived in a truck", false)
  val index = new InvertedIndex
  index.addDocumentToIndex(doc1, doc2, doc3)
  val searchRanker = new SearchRanker(index)

  @Test
  def testTFs() = {
    //docs
    assertEquals(1, searchRanker.tf("a", doc1), 0.001)
    assertEquals(1, searchRanker.tf("a", doc2), 0.001)
    assertEquals(1, searchRanker.tf("a", doc3), 0.001)
    assertEquals(0, searchRanker.tf("arrived", doc1), 0.001)
    assertEquals(1, searchRanker.tf("a", doc2), 0.001)
    assertEquals(1, searchRanker.tf("a", doc3), 0.001)
    //query
    assertEquals(1, searchRanker.tf("gold", query), 0.001)
  }

  @Test
  def testIDFs() = {
    //docs
    assertEquals(0.0, searchRanker.idf("a"), 0.001)
    assertEquals(0.1761, searchRanker.idf("arrived"), 0.001)
    assertEquals(0.4771, searchRanker.idf("damaged"), 0.001)
    assertEquals(0.1761, searchRanker.idf("shipment"), 0.001)
    //query
    assertEquals(0.1761, searchRanker.idf("gold"), 0.001)
  }

  @Test
  def testTFIDFs() = {
    //docs
    assertEquals(0.0, searchRanker.tfidf("a", doc1), 0.001)
    assertEquals(0.1761, searchRanker.tfidf("arrived", doc2), 0.001)
    assertEquals(0.0, searchRanker.tfidf("damaged", doc2), 0.001)
    assertEquals(0.4771, searchRanker.tfidf("damaged", doc1), 0.001)
    assertEquals(0.9542, searchRanker.tfidf("silver", doc2), 0.001)
    assertEquals(0.0, searchRanker.tfidf("silver", doc1), 0.001)
    //query
    assertEquals(0.1761, searchRanker.tfidf("gold", query), 0.001)
    assertEquals(0.4771, searchRanker.tfidf("silver", query), 0.001)
    assertEquals(0.1761, searchRanker.tfidf("truck", query), 0.001)
  }
  
  @Test
  def testVectorWeights() = {
    //docs
    assertEquals(0.7192, searchRanker.vectorWeights(doc1), 0.001)
    assertEquals(1.0955, searchRanker.vectorWeights(doc2), 0.001)
    assertEquals(0.3522, searchRanker.vectorWeights(doc3), 0.001)
    //query
    assertEquals(0.5382, searchRanker.vectorWeights(query), 0.001)
  }
  
  @Test
  def testDotProducts() = {
    assertEquals(0.0310, searchRanker.dotProduct(query, doc1), 0.001)
    assertEquals(0.4862, searchRanker.dotProduct(query, doc2), 0.001)
    assertEquals(0.0620, searchRanker.dotProduct(query, doc3), 0.001)
  }
  
  @Test
  def testSimilarity() = {
    assertEquals(0.0801, searchRanker.similarity(query, doc1), 0.001)
    assertEquals(0.8246, searchRanker.similarity(query, doc2), 0.001)
    assertEquals(0.3271, searchRanker.similarity(query, doc3), 0.0011)
  }
  
  
}