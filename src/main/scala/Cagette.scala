package cagette

trait Cageot[A <: {def id:ID},ID] {

	sealed trait Pagination
	case class Page(page: Int, size: Int = 25) extends Pagination
	case object Full extends Pagination

	lazy private val store = collection.mutable.ListBuffer.empty[A] ++= initialData

	// -- Operations

	def findById(id: ID): Option[A] = {
		store.find(item => item.id == id)
	}

	def findAll(pagination: Pagination = Full): Seq[A] = {
		paginate(store.toSeq, pagination)
	}

	def findBy(p: A => Boolean, pagination: Pagination = Full): Seq[A] = {
		paginate(store.filter(p), pagination)
	}

	def findOneBy(p: A => Boolean): Option[A] = {
		findBy(p).headOption
	}

	def delete(id: ID) {
		findById(id).foreach(i => store -= i)
	}

	def delete(ids: Seq[ID]) {
		ids.foreach(delete)
	}

	def delete(p: A => Boolean) {
		delete(findBy(p).map(_.id))
	}

	def save(item: A): A = {
		delete(item.id)
		store += item
		item
	}

	def save(items: Seq[A]) {
		items.foreach(save)
	}

	def count(p: A => Boolean): Long = {
		findBy(p).size
	}

	def clear() {
		store.clear()
	}

	def size: Long = {
		store.size
	}

	// -- Utils

	private def paginate(items: Seq[A], pagination: Pagination): Seq[A] = pagination match {
		case Full => items
		case Page(page, size) => items.drop( (page-1) * size).take(size)
	}

	// -- Initial data (override to feed with initial data)

	def initialData: Seq[A] = Nil

	// -- ID Generators

	lazy private val counter = new java.util.concurrent.atomic.AtomicLong(0)

	def autoIncrement: Long = {
		counter.getAndIncrement()
	}

	def UUID: String = {
		java.lang.Long.toString(autoIncrement, 24)
	}

}
