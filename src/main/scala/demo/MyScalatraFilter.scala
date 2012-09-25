package net.srirangan
import org.scalatra._
import java.net.URL
import scalate.ScalateSupport
import search.managers.SearchManager
import java.io.File
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import search.documents.MockDocumentManager
import search.documents.MockDocument
import search.documents.NationalArchiveDocument
import search.documents.NationalArchiveDocumentManager

case class Person(name: String, age: Int)

class MyScalatraFilter extends ScalatraFilter with ScalateSupport {

  override implicit val contentType = "text/html"
  private val searchManager = new SearchManager[NationalArchiveDocument]()
  private val documentManager = new NationalArchiveDocumentManager()
  searchManager.addToIndex(documentManager.parse("src/resources/PhotoMetaData10000.csv"))

  get("/") {
    scaml("home")
  }

  get("/search") {
    val queryString = params("query")
    val results = searchManager.query(queryString)
    val json = ("results" -> results.map { p => (("name" -> p.document.barcode) ~ ("score" -> p.score)) })
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
