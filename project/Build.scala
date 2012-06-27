import sbt._
import sbt.Keys._

object CagetteBuild extends Build {

	val main = Project(id = "cagette", base = file(".")).settings(
		version := "0.2-SNAPSHOT",
		organization := "guillaumebort",
		publishTo := Some(Resolver.file("file", file("../repository")))
	)

}