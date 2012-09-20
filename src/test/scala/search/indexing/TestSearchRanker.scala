package search.indexing

import org.junit.Assert._
import scalaz._
import Scalaz._
import effects._
import org.junit.Test
import search.documents.MockDocumentManager
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import search.documents.MockDocument

class TestSearchRanker {

  val documentManager = new MockDocumentManager()

  private def createTestData(input: String, filename: String): File = {
    val file = new File(filename)
    val out = new BufferedWriter(new FileWriter(file))
    out.write(input)
    out.close()
    file
  }

  @Test
  def testCalculateTermInDocumentFrequency() = {
    val searchRanker = new SearchRanker(new InvertedIndex[MockDocument])
    val file = createTestData("This is a test string. It is designed to test the search ranker. In this test", "testCalculateTermFrequency")
    val document = documentManager.parse(file)
    assertEquals(0.0, searchRanker.index.tf("is", document), 0.1)
    assertEquals(0.428, searchRanker.index.tf("test", document), 0.1);
  }

  @Test
  def testCalculateTermInDocumentFrequency2() = {
    val searchRanker = new SearchRanker(new InvertedIndex[MockDocument])
    val document = documentManager.parse(new File("src/resources/documents/bible/Genesis.txt"))
    assertEquals(0.00007289161, searchRanker.index.tf("moses", document), 0.1);
  }

  @Test
  def testCalculateInverseDocumentFrequency() = {
    val index = new InvertedIndex[MockDocument];
    val documentManager = new MockDocumentManager();
    index.addDocumentToIndex(documentManager.parse(createTestData("Testey data and stuff", "test1")));
    index.addDocumentToIndex(documentManager.parse(createTestData("Test data and another stuff", "test2")));
    index.addDocumentToIndex(documentManager.parse(createTestData("Testey test data and more stuff", "test3")));
    val searchRanker = new SearchRanker(index);
    assertEquals(1.17609, searchRanker.index.idf("testey"), 0.1);
  }


}