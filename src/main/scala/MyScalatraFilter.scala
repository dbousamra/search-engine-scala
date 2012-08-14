package net.srirangan

import org.scalatra._
import java.net.URL
import scalate.ScalateSupport
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

class MyScalatraFilter extends ScalatraFilter with ScalateSupport {
  
  override implicit val contentType = "text/html"
  
  get("/") {
    layoutTemplate("/WEB-INF/scalate/templates/hello-scalate.jade")
  }
  
  get("/names") {
    val contentType = "text/json"
    val json = ("name" -> "Dominic")
    compact(render(json))
  }

  notFound {
    // If no route matches, then try to render a Scaml template
    val templateBase = requestPath match {
      case s if s.endsWith("/") => s + "index"
      case s => s
    }
    val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".ssp"
    servletContext.getResource(templatePath) match {
      case url: URL => 
        templateEngine.layout(templatePath)
      case _ => 
        filterChain.doFilter(request, response)
    } 
  }
}
