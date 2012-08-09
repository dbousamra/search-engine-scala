package search.documents

import java.io.File

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

import search.managers.DocumentManager

class TestDocument {

  private val documentManager = new DocumentManager()
  private val document = getTestDocument();

  private def getTestDocument() = {
    documentManager.parseFile(new File("src/resources/documents/bible/Genesis.txt"))
  }

  @Test
  def testGetId() = {
    assertEquals(documentManager.documentCount, document.id)
  }

  @Test
  def testGetFile() = {
    assertEquals("src/resources/documents/bible/Genesis.txt", document.file.getPath())
  }

  @Test
  def testGetWords() = {
    assertFalse(document.words.isEmpty)
  }

}


