package search.managers

import java.io.File
import org.junit.Assert._
import org.junit.Test

class TestSearchManager {
  
  val searchManager = new SearchManager()
  
  @Test
  def testAddFileToIndex() = {
    val document = searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    assertEquals(99, searchManager.index.index.get("called").get(document))
  }
  
  @Test
  def testQuery() = {
    searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    searchManager.addFileToIndex(new File("src/resources/documents/bible/Exodus.txt"))
    assertEquals("Exodus.txt", searchManager.query("moses").head._1.file.getName())
  }
  
  @Test
  def testIndexAll() = {
    val folder = new File("src/resources/documents/bible")
    searchManager.addFolderToIndex(folder)
    assertEquals(69, searchManager.index.getAllDocuments.length)
  }

}