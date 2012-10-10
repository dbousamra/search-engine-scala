package download

import search.documents.NationalArchiveDocument
import search.documents.NationalArchiveDocumentManager
import java.io._

object ImageDownloader {

	def main(args: Array[String]): Unit = {
    val documents = new NationalArchiveDocumentManager().parse("src/resources/PhotoMetaData.csv")

    documents.foreach{ x => 
      val source = scala.io.Source.fromURL(x.smallImageURL)
      println(x.barcode)
      val writer = new PrintWriter(new File("images", x.barcode + ".jpg"))
      writer.write(source.mkString(""))
      writer.close()
    }
  }

}