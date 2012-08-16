package search.managers

import java.io.File
import org.junit.Test
import org.junit.Assert._ 

class TestSearchManagerOnMopp {
  
  val searchManager = SearchManager(new File("src/resources/documents/mopp"))
 
  @Test
  def testIndexAll() = {
    assertEquals(291, searchManager.index.getAllDocuments.length)
  }
  
  @Test
  def testQuery() = {
    val queryResult = searchManager.query("supplementary")
    println(queryResult)
    assertEquals(9, queryResult.length)
  }
  
  @Test
  def testQueryMultiple() = {
    val queryResult = searchManager.query("guild supple")
    println(queryResult)
    assertEquals(9, queryResult.length)
  }
  
}