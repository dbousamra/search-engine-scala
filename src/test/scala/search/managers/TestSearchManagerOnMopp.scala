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
    assertEquals(9, queryResult.length)
  }
  
  
  @Test
  def testQueryGuild() = {
    val queryResult = searchManager.query("guild")
    assertEquals(24, queryResult.length)
  }
  @Test
  def testQueryMultiple() = {
    val queryResult = searchManager.query("guild supplementary")
    assertEquals(33, queryResult.length)
  }
  
  @Test
  def quickTest() = {
    val queryResult = searchManager.query("supplementary")
  }
}