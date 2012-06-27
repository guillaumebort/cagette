# Cagette, the quick and dirty data store for prototyping

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