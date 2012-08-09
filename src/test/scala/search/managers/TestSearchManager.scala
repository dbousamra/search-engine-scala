package search.managers

import java.io.File
import org.junit.Assert.assertNotNull
import org.junit.Test

class TestSearchManager {
  
  val searchManager = new SearchManager()
  
  @Test
  def testAddFileToIndex() = {
    searchManager.addFileToIndex(new File("src/resources/documents/bible/Genesis.txt"))
    assertNotNull(searchManager.index.index.get("moses"))
  }

}