package net.srirangan
import org.scalatra._
import java.net.URL
import scalate.ScalateSupport
import search.managers.SearchManager
import java.io.File
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import search.documents.NationalArchiveDocument
import search.documents.NationalArchiveDocumentManager
import search.managers.LuceneSearchManager

class MyScalatraFilter extends ScalatraServlet with ScalateSupport {

  override implicit val contentType = "text/html"
  private val searchManager = new SearchManager[NationalArchiveDocument]()
  private val documentManager = new NationalArchiveDocumentManager()
  searchManager.addToIndex(documentManager.parse("src/resources/PhotoMetaData10000Replaced.csv"))

  get("/") {
    scaml("home")
  }

  get("/timeline") {
    scaml("timeline")
  }

  get("/timeline/data") {
    val queryString = "gold coast"
    val results = searchManager.query(queryString)
    val json = (
      ("timeline") ->
      ("headline" -> "National Archives of Australia")
      ~ ("text" -> "Search stuff")
      ~ ("type" -> "default")
      ~ ("date" -> results.map {
        p =>
          (
            ("startDate" -> p.document.year.toString)
            ~ ("headline" -> p.document.barcode)
            ~ ("text" -> p.document.description)
            ~ ("asset" -> ("media" -> p.document.largeImageURL)))
      }))
    pretty(render(json))
  }

  get("/search") {
    val queryString = params("query")
    val results = searchManager.query(queryString)
    val min = if (results.isEmpty) 0 else results.minBy(_.document.year).document.year
    val max = if (results.isEmpty) 0 else results.maxBy(_.document.year).document.year
    val json = (
      ("results" -> results.map (
        p =>
          (
            ("barcode" -> p.document.barcode)
            ~ ("description" -> p.document.description)
            ~ ("score" -> p.score)
            ~ ("year" -> p.document.year)
            ~ ("smallImageURL" -> p.document.smallImageURL)
            ~ ("largeImageURL" -> p.document.largeImageURL)
            ~ ("location" -> p.document.location))
      ))
      ~ ("resultsLength" -> results.length) 
      ~ ("startDate" -> min)
      ~ ("endDate" -> max))
    pretty(render(json))
  }

}
