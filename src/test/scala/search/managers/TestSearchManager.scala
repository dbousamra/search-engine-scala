package search.managers

import java.io.File
import org.junit.Assert._
import org.junit.Test
import org.junit.BeforeClass
import search.documents.MockDocumentManager
import search.documents.MockDocument

class TestSearchManager {

  private val searchManager = new SearchManager[MockDocument]
  private val documentManager = new MockDocumentManager
  searchManager.addToIndex(documentManager.parseFolder(new File("src/resources/documents/bible")))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Exodus.txt"))
//  searchManager.addFileToIndex(new File("src/resources/documents/bible/Ezekiel.txt"))

  @Test
  def testQuery() = {
    val q = searchManager.query("moses")
    assertEquals("Exodus.txt", q.head.document.name)
  }

  @Test
  def testIndexAll() = {
    assertEquals(69, searchManager.index.getAllDocuments.length)
  }
}