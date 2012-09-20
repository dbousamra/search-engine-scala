package search.indexing

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import search.documents.MockDocumentManager
import search.documents.MockDocument
import search.documents.QueryDocumentManager
import search.documents.QueryDocumentManager

class TestSearchRanker2 {

  //based on http://www.miislita.com/term-vector/term-vector-3.html

  val documentManager = new MockDocumentManager
  val query = new QueryDocumentManager().parseText("gold silver truck", false)
  val doc1 = documentManager.parseText("Shipment of gold damaged in a fire", false)
  val doc2 = documentManager.parseText("Delivery of silver arrived in a silver truck", false)
  val doc3 = documentManager.parseText("Shipment of gold arrived in a truck", false)
  val index = new InvertedIndex[MockDocument]
  index.addDocumentToIndex(doc1, doc2, doc3)
  val searchRanker = new SearchRanker(index)

  @Test
  def testTFs() = {
    //docs
    assertEquals(1, searchRanker.index.tf("a", doc1), 0.001)
    assertEquals(1, searchRanker.index.tf("a", doc2), 0.001)
    assertEquals(1, searchRanker.index.tf("a", doc3), 0.001)
    assertEquals(0, searchRanker.index.tf("arrived", doc1), 0.001)
    assertEquals(1, searchRanker.index.tf("a", doc2), 0.001)
    assertEquals(1, searchRanker.index.tf("a", doc3), 0.001)
    //query
    assertEquals(1, searchRanker.index.tf("gold", query), 0.001)
  }

  @Test
  def testIDFs() = {
    //docs
    assertEquals(0.0, searchRanker.index.idf("a"), 0.001)
    assertEquals(0.1761, searchRanker.index.idf("arrived"), 0.001)
    assertEquals(0.4771, searchRanker.index.idf("damaged"), 0.001)
    assertEquals(0.1761, searchRanker.index.idf("shipment"), 0.001)
    //query
    assertEquals(0.1761, searchRanker.index.idf("gold"), 0.001)
  }

  @Test
  def testTFIDFs() = {
    //docs
    assertEquals(0.0, searchRanker.index.tfidf("a", doc1), 0.001)
    assertEquals(0.1761, searchRanker.index.tfidf("arrived", doc2), 0.001)
    assertEquals(0.0, searchRanker.index.tfidf("damaged", doc2), 0.001)
    assertEquals(0.4771, searchRanker.index.tfidf("damaged", doc1), 0.001)
    assertEquals(0.9542, searchRanker.index.tfidf("silver", doc2), 0.001)
    assertEquals(0.0, searchRanker.index.tfidf("silver", doc1), 0.001)
    //query
    assertEquals(0.1761, searchRanker.index.tfidf("gold", query), 0.001)
    assertEquals(0.4771, searchRanker.index.tfidf("silver", query), 0.001)
    assertEquals(0.1761, searchRanker.index.tfidf("truck", query), 0.001)
  }
  
  @Test
  def testVectorWeights() = {
    //docs
    assertEquals(0.7192, searchRanker.index.vectorWeights(doc1), 0.001)
    assertEquals(1.0955, searchRanker.index.vectorWeights(doc2), 0.001)
    assertEquals(0.3522, searchRanker.index.vectorWeights(doc3), 0.001)
    //query
    assertEquals(0.5382, searchRanker.index.vectorWeights(query), 0.001)
  }
  
  @Test
  def testDotProducts() = {
    assertEquals(0.0310, searchRanker.index.dotProduct(query, doc1), 0.001)
    assertEquals(0.4862, searchRanker.index.dotProduct(query, doc2), 0.001)
    assertEquals(0.0620, searchRanker.index.dotProduct(query, doc3), 0.001)
  }
  
  @Test
  def testSimilarity() = {
    println(index.index)
    println(index.weights)
    assertEquals(0.0801, searchRanker.index.similarity(query, doc1), 0.001)
    assertEquals(0.8246, searchRanker.index.similarity(query, doc2), 0.001)
    assertEquals(0.3271, searchRanker.index.similarity(query, doc3), 0.0011)
  }
  
  
}