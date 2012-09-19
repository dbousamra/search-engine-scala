package net.srirangan
import org.scalatra._
import java.net.URL
import scalate.ScalateSupport
import search.managers.SearchManager
import java.io.File
import net.liftweb.json.JsonDSL._
import net.liftweb.json._

case class Person(name: String, age: Int)

class MyScalatraFilter extends ScalatraFilter with ScalateSupport {

  override implicit val contentType = "text/html"
  private val searchManager = SearchManager(new File("src/resources/documents/mopp"))

  get("/") {
    scaml("home")
  }

  get("/search") {
    val queryString = params("query")
    val results = searchManager.query(queryString)
    val json = ("results" -> results.map { p => (("name" -> p.document._name) ~ ("score" -> p.score)) })
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
