package search.indexing

import org.junit.Assert._
import scalaz._
import Scalaz._
import effects._
import org.junit.Test
import search.managers.DocumentManager
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

class TestSearchRanker {

  val documentManager = new DocumentManager()

  private def createTestData(input: String, filename: String): File = {
    val file = new File(filename)
    val out = new BufferedWriter(new FileWriter(file))
    out.write(input)
    out.close()
    file
  }

  @Test
  def testCalculateTermInDocumentFrequency() = {
    val searchRanker = new SearchRanker(new InvertedIndex())
    val file = createTestData("This is a test string. It is designed to test the search ranker. In this test", "testCalculateTermFrequency")
    val document = documentManager.parseFile(file)
    assertEquals(0.0, searchRanker.tf("is", document), 0.1)
    assertEquals(0.428, searchRanker.tf("test", document), 0.1);
  }

  @Test
  def testCalculateTermInDocumentFrequency2() = {
    val searchRanker = new SearchRanker(new InvertedIndex())
    val document = documentManager.parseFile(new File("src/resources/documents/bible/Genesis.txt"))
    assertEquals(0.00007289161, searchRanker.tf("moses", document), 0.1);
  }

  @Test
  def testCalculateInverseDocumentFrequency() = {
    val index = new InvertedIndex();
    val documentManager = new DocumentManager();
    index.addDocumentToIndex(documentManager.parseFile(createTestData("Testey data and stuff", "test1")));
    index.addDocumentToIndex(documentManager.parseFile(createTestData("Test data and another stuff", "test2")));
    index.addDocumentToIndex(documentManager.parseFile(createTestData("Testey test data and more stuff", "test3")));
    val searchRanker = new SearchRanker(index);
    assertEquals(1.17609, searchRanker.idf("testey"), 0.1);
  }


}