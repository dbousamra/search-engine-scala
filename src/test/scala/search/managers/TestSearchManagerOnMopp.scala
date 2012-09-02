package search.managers

import java.io.File
import org.junit.Test
import org.junit.Assert._ 
import org.junit.BeforeClass

object TestSearchManagerOnMopp {

    val searchManager = SearchManager(new File("src/resources/documents/mopp"))


}

class TestSearchManagerOnMopp {
  
  
 
  @Test
  def testIndexAll() = {
    assertEquals(291, TestSearchManagerOnMopp.searchManager.index.getAllDocuments.length)
  }
  
  @Test
  def testQuery() = {
    val queryResult = TestSearchManagerOnMopp.searchManager.query("supplementary")
    assertEquals(9, queryResult.length)
  }
  
  
  @Test
  def testQueryGuild() = {
    val queryResult = TestSearchManagerOnMopp.searchManager.query("guild")
    assertEquals(24, queryResult.length)
  }
  @Test
  def testQueryMultiple() = {
    val queryResult = TestSearchManagerOnMopp.searchManager.query("qut")
    assertEquals(33, queryResult.length)
  }
  
  @Test
  def quickTest() = {
    val queryResult = TestSearchManagerOnMopp.searchManager.query("supplementary")
  }
}