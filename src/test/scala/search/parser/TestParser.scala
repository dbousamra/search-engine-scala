package search.parser

import org.junit._
import Assert._
import search.parsing.Parser
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import org.junit.rules.TemporaryFolder
import search.parsing.Parser._

class TestParser {
  

  private val parser = new Parser()

  private def createTestData(input: String, filename: String): File = {
    val file = new File(filename)
    val out = new BufferedWriter(new FileWriter(file))
    out.write(input)
    out.close()
    file
  }
  
  @Test
  def testParser() = {
    val words = parser.parse(new File("src/resources/documents/bible/Genesis.txt"))
    assertFalse(words.isEmpty)
  }
  
  @Test
  def testStopWords() = {
    val tempFile = createTestData("Hello this is a test document", "temp1")
    val words = parser.parse(tempFile)
    assertFalse(Set("this", "is", "a") forall (words contains))
    assertTrue(Set("hello", "test", "document") forall (words contains))
  }
  

}