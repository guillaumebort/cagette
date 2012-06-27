# Cagette

> Cagette is a quick and dirty data store for prototyping

## Setting up with sbt

Configure a new resolver:

```scala
resolvers += "Guillaume Bort" at "http://guillaume.bort.fr/repository"
```

Add the library dependency:

```scala
libraryDependencies += "guillaume.bort" %% "cagette" % "0.1"
```

## Using – as easy as 1,2,3

### 1. Create your domain class:

Let's try with a `User` class with an `id` field of any type:

```scala
case class User(id: Long, email: String, groups: Seq[String])
```

### 2. Create a `Cageot[DomainType,IdType]` to store the `User` instances:

We usually name the `Cageot` with the same name as the domain class:

```scala
object User extends cagette.Cageot[User,Long]
```

### 3. Query your cagette using Scala functions

The `Cageot` type provide some convenient higher order query functions you can use directly:

#### findAll()

```scala
val allusers: Seq[User] = User.findAll()
```

#### findById(id: IdType)

```scala
val maybeUser: Option[User] = User.findById(100)
```

#### findOneBy(predicate: DomainType => Boolean)

```scala
val maybeUser: Option[User] = User.findOneBy(_.email == "coco@gmail.com")
```

#### findBy(predicate: DomainType => Boolean)

```scala
val adminUsers: Seq[User] = User.findBy(_.groups.contains("admin"))
```

## Setting up initial data set

While defining your `Cageot` you can define the initial data set it will contain:

```scala
object User extends cagette.Cageot[User,Long] {
	
	override def initialData = Seq(
		User(1, "bob@gmail.com", groups = Seq("admin,user")),
		User(2, "kiki@gmail.com", groups = Seq("user")),
		User(3, "toto@gmail.com", groups = Nil),
	)

}
```

## And even more...

Check the code source and find secret features like __pagination__ and __autoincrement__ for domain class identifiers.

Enjoy!