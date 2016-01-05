import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayJava
name := """style-admin"""

version := "0.1.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1205-jdbc42" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis" % "3.3.0" withSources() withJavadoc()

libraryDependencies += "com.google.inject" % "guice" % "4.0" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.6" withSources() withJavadoc()

libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "4.0" withSources() withJavadoc()

libraryDependencies += "com.github.mumoshu" %% "play2-memcached-play24" % "0.7.0" withSources() withJavadoc()

libraryDependencies += "com.aliyun.oss" % "aliyun-sdk-oss" % "2.0.1" withSources() withJavadoc()
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.5.0" withSources() withJavadoc()

libraryDependencies += "org.apache.poi" % "poi" % "3.13" withSources() withJavadoc()
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.13" withSources() withJavadoc()

// 检查代码中使用的过时类细节
javacOptions += "-Xlint:deprecation"
//  javacOptions ++= Seq("-source", "1.8", "-target", "1.8","-deprecation")
scalacOptions ++= Seq("-unchecked", "-deprecation","-feature")