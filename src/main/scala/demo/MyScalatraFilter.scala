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

case class Person(name: String, age: Int)

class MyScalatraFilter extends ScalatraFilter with ScalateSupport {

  override implicit val contentType = "text/html"
  private val searchManager = new LuceneSearchManager[NationalArchiveDocument]()
  private val documentManager = new NationalArchiveDocumentManager()
  searchManager.addToIndex(documentManager.parse("src/resources/PhotoMetaData10000.csv"))

  get("/") {
    scaml("home")
  }

  get("/search") {
    val queryString = params("query")
    val results = searchManager.query(queryString)
    val json = ("results" -> results.map { 
      p => (
        ("barcode" -> p.document.barcode)
      ~ ("description" -> p.document.description) 
      ~ ("score" -> p.score)
      ~ ("year" -> p.document.year) 
      ~ ("smallImageURL" -> p.document.smallImageURL)
      )})
    compact(render(json))
  }

  notFound {
    // If no route matches, then try to render a Scaml template
    val templateBase = requestPath match {
      case s if s.endsWith("/") => s + "index"
      case s => s
    }
    val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".scaml"
    servletContext.getResource(templatePath) match {
      case url: URL =>
        templateEngine.layout(templatePath)
      case _ =>
        filterChain.doFilter(request, response)
    }
  }
}
