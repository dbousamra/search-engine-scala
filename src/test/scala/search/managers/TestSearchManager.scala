package search.managers

import java.io.File
import org.junit.Assert._
import org.junit.Test
import org.junit.BeforeClass

class TestSearchManager {

  private val searchManager = SearchManager(new File("src/resources/documents/bible/"))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Exodus.txt"))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Ezekiel.txt"))

  @Test
  def testQuery() = {
    println(searchManager.index.index)
    val q = searchManager.query("moses")
    println(q)
    assertEquals("Exodus.txt", q.head.document.name)
  }

  @Test
  def testIndexAll() = {
    assertEquals(69, searchManager.index.getAllDocuments.length)
  }
}