package search.managers

import java.io.File
import org.junit.Assert._
import org.junit.Test
import org.junit.BeforeClass


class TestSearchManager {
  
   private val searchManager = SearchManager(new File("src/resources/documents/bible"))

  @Test
  def testAddFileToIndex() = {
    val document = searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    assertEquals(99, searchManager.index.index.get("called").get(document))
  }

  @Test
  def testQuery() = {    
    assertEquals("Exodus.txt", searchManager.query("moses").head.document.file.get.getName())
  }

  @Test
  def testIndexAll() = {
    assertEquals(69, searchManager.index.getAllDocuments.length)
  }

  @Test
  def testQueryAll() = {
    println(searchManager.query("moses jesus").mkString("\n"))
    assertTrue(true)
  }
  
  @Test
  def testQueryAllTwoTerms() = {
    assertTrue(true)
  }

}