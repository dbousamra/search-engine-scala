package search.managers

import java.io.File

import org.junit.Assert.assertEquals
import org.junit.Test

class TestDocumentManager {

  private val documentManager = new DocumentManager()

  @Test
  def testCountOne() = {
    val document = documentManager.parseFile(new File("src/resources/documents/bible/Genesis.txt"))
    val document2 = documentManager.parseFile(new File("src/resources/documents/bible/Exodus.txt"))
    val document3 = documentManager.parseFile(new File("src/resources/documents/bible/Acts.txt"))
    assertEquals(3, documentManager.documentCount)
  }

  @Test
  def testCountTwo() = {
    val document = documentManager.parseFile(new File("src/resources/documents/bible/Genesis.txt"))
    val document2 = documentManager.parseFile(new File("src/resources/documents/bible/Exodus.txt"))
    assertEquals(2, documentManager.documentCount)
  }
}


