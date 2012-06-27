import sbt._
import sbt.Keys._

object CagetteBuild extends Build {

	val main = Project(id = "cagette", base = file(".")).settings(
		version := "0.2-SNAPSHOT",
		organization := "guillaume.bort",

		libraryDependencies += "org.specs2" %%   "specs2" % "1.9" % "test",

		publishTo := Some(Resolver.file("file", file(Option(System.getProperty("repository.path")).getOrElse("/tmp"))))
	)

}