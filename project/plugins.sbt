resolvers += Classpaths.typesafeResolver

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

addSbtPlugin("com.typesafe.startscript" % "xsbt-start-script-plugin" % "0.5.3")

addSbtPlugin("com.github.siasia" % "xsbt-web-plugin_2.9.2" % "0.12.0-0.2.11.1")