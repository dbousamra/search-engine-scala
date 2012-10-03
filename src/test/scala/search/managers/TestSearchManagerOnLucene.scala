package search.managers

import java.io.File
import org.junit.Test
import org.junit.Assert._
import org.junit.BeforeClass
import search.managers.SearchManager
import search.documents.MockDocument
import search.documents.MockDocumentManager

object TestSearchManagerOnLucene {
  val searchManager = new LuceneSearchManager[MockDocument]
  searchManager.addToIndex(new MockDocumentManager().parseFolder(new File("src/resources/documents/mopp")))
}

class TestSearchManagerOnLucene {

  @Test
  def testQuery() = {
    val queryResult = TestSearchManagerOnLucene.searchManager.query("supplementary")
    println(queryResult)
    assertEquals(9, queryResult)
  }

  
}