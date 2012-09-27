package search.documents

import au.com.bytecode.opencsv.CSVReader
import scala.collection.JavaConverters._
import java.io.FileReader
import search.parsing.Parser
import search.parsing.Parser._

class NationalArchiveDocument(
    val barcode: String, 
    title: List[String],
    val description: String,
    val year: Int, 
    val location: String, 
    val largeImageURL: String, 
    val smallImageURL: String) extends Document(title)

class NationalArchiveDocumentManager {

  private val parser = new Parser()

  val photos = "src/resources/PhotoMetaData.csv"

  def parse(filename: String): Seq[NationalArchiveDocument] = {
    val reader = new CSVReader(new FileReader(filename));
//    reader.readAll().asScala.tail.map(parseRow).toList
    
    val iterator = Iterator.continually(reader.readNext()).takeWhile(_ != null)
    iterator.toSeq.tail.map(parseRow)
  }

  def parseRow(row: Array[String]): NationalArchiveDocument = {
    new NationalArchiveDocument(
      barcode = row(0),
      description = row(1),
      title = parser.parse(row(1)),
      year = row(2).toInt,
      location = row(3),
      largeImageURL = row(4),
      smallImageURL = row(5))
  }
}