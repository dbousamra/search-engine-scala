package search.documents

import java.io.File

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class TestDocument {

  private val documentManager = new MockDocumentManager()
  private val document = getTestDocument();

  private def getTestDocument() = {
    documentManager.parse(new File("src/resources/documents/bible/Genesis.txt"))
  }

  @Test
  def testGetFile() = {
    assertEquals("src/resources/documents/bible/Genesis.txt", document.file.get.getPath())
  }

  @Test
  def testGetWords() = {
    assertFalse(document.words.isEmpty)
  }
  
  @Test
  def testGetWordCount() = {
    assertEquals(230, document.getWordCount("god"))
  }

}


