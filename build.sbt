organization := "com.github.dbousamra"

name := "search-engine-scala"

version := "0.0.1"

scalaVersion := "2.9.1"

seq(webSettings :_*)

classpathTypes ~= (_ + "orbit")

libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-json" % "2.4",
  "net.sf.opencsv" % "opencsv" % "2.0",
  "junit" % "junit" % "4.8.1" % "test",
  "org.scalatest" %% "scalatest" % "1.8" % "test",
  "org.scalaz" %% "scalaz-core" % "6.0.4",
  "org.scalatra" % "scalatra" % "2.1.1",
  "org.scalatra" % "scalatra-scalate" % "2.1.1",
  "org.scalatra" % "scalatra-specs2" % "2.1.1" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
  "edu.mit" % "jwi" % "2.2.1",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container;test;provided",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
)