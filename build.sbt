name := """style-adbk"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1103-jdbc41" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis" % "3.3.0" withSources() withJavadoc()

libraryDependencies += "com.google.inject" % "guice" % "4.0" withSources() withJavadoc()

libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.6" withSources() withJavadoc()

libraryDependencies += "com.google.inject.extensions" % "guice-multibindings" % "4.0" withSources() withJavadoc()

// libraryDependencies += "com.github.mumoshu" %% "play2-memcached" % "0.6.0" withSources() withJavadoc()

// libraryDependencies += "com.aliyun.oss" % "aliyun-sdk-oss" % "2.0.1" withSources() withJavadoc()




