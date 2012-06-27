package cagette.test

import org.specs2.mutable._

import cagette.{ Cageot, Identifier }

case class User(email: String, name: String)
case class Project(id: Long = Project.autoIncrement, name: String, owner: String, members: Seq[String] = Nil)

object User extends Cageot[User,String]()(Identifier(_.email)) {

	override def initialData = Seq(
		User(email = "bob@gmail.com", "Bob D."),
		User(email = "nicolas@gmail.com", "Nicolas S."),
		User(email = "jean@gmail.com", "Jean CV.")
	)

}

object Project extends Cageot[Project,Long] {

	override def initialData = Seq(
		Project(name = "Secret", owner = "jean@gmail.com"),
		Project(name = "Ariane", owner = "nicolas@gmail.com", members = Seq("bob@gmail.com", "jean@gmail.com"))
	)

}

class CagetteSpec extends Specification {

	"Testing the Cageot" should {

		"find all" in {
			User.findAll() must have size(3)
		}

	  	"find User by email" in {
	   		User.findById("bob@gmail.com") must beSome.which(_.name == "Bob D.")
	    	User.findById("kiki@gmail.com") must beNone
	  	}

	  	"find User by name like" in {
	  		User.findOneBy(_.name contains "Jean") must beSome.which(_.email == "jean@gmail.com")
	  		User.findOneBy(_.name contains "Guillaume") must beNone
	  	}

	  	"find all Users who own at least one project" in {
	  		User.findBy(user => Project.findOneBy(_.owner == user.email).isDefined) must have size(2)
	  	}

	  	"find all Project for Jean" in {
	  		val jean = "jean@gmail.com"
	  		Project.findBy(project => project.owner == jean || project.members.contains(jean)) must have size(2)
	  	}

	  	"find all Project for Bob" in {
	  		val bob = "bob@gmail.com"
	  		Project.findBy(project => project.owner == bob || project.members.contains(bob)) must have size(1)
	  	}
	  
	}

}