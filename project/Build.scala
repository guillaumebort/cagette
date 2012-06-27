import sbt._
import sbt.Keys._

object CagetteBuild extends Build {

	val main = Project(id = "cagette", base = file(".")).settings(
		version := "0.2-SNAPSHOT",
		organization := "guillaume.bort",
		publishTo := Some(Resolver.file("file", file(Option(System.getProperty("repository.path")).getOrElse(sys.error("Please set -Drepository.path")))))
	)

}