package search.managers

import java.io.File
import org.junit.Assert._
import org.junit.Test
import org.junit.BeforeClass

object TestSearchManager {
  private val searchManager = new SearchManager()
  searchManager.addFolderToIndex(new File("src/resources/documents/bible"))
}

class TestSearchManager {

  @Test
  def testAddFileToIndex() = {
    val document = TestSearchManager.searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    assertEquals(99, TestSearchManager.searchManager.index.index.get("called").get(document))
  }

  @Test
  def testQuery() = {
    TestSearchManager.searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    TestSearchManager.searchManager.addFileToIndex(new File("src/resources/documents/bible/Exodus.txt"))
    assertEquals("Exodus.txt", TestSearchManager.searchManager.query("moses").head._1.file.getName())
  }

  @Test
  def testIndexAll() = {
    assertEquals(69, TestSearchManager.searchManager.index.getAllDocuments.length)
  }

  @Test
  def testQueryAll() = {
    println(TestSearchManager.searchManager.query("moses"))
    assertTrue(true)
  }

}