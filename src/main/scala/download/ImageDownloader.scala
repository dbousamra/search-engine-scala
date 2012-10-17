package download

import search.documents.NationalArchiveDocument
import search.documents.NationalArchiveDocumentManager
import java.io._

object ImageDownloader {

	def main(args: Array[String]): Unit = {
    val documents = new NationalArchiveDocumentManager().parse("someFile")
    // val documents = new NationalArchiveDocumentManager().parse("src/resources/PhotoMetaData10000.csv")
    val out = new java.io.FileWriter(new File("someFile2"))
    val urls = documents.foreach { x => 
      // val source = scala.io.Source.fromURL(x.smallImageURL)
      // println(x.barcode)
      // val writer = new PrintWriter(new File("images", x.barcode + ".jpg"))
      // writer.write(source.mkString(""))
      // writer.close()
      val lo = "images/" + x.barcode+ ".jpg"
      out.write(x.barcode + "," + "\"" + x.description.replaceAll("\"", "\"\"") + "\"" + "," + x.year.toString + "," + x.location + "," + lo + "," + lo + "\n")

    }
    out.close
  }
}