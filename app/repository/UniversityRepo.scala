package repository

import javax.inject.{Inject, Singleton}
import models.University
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.Date
import scala.concurrent.Future

@Singleton()
class UniversityRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UniversityTable with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  def create(university: University): Future[Int] = db.run {
    (universityTableQuery returning universityTableQuery.map(_.id)) += university
  }


  def update(university: University):Future[Int] = db.run {
    universityTableQuery.filter(_.id === university.id).update(university)
  }




  def getById(id: Int) = db.run {
    universityTableQuery.filter(_.id === id).result.headOption
  }


  def getAll(): Future[List[University]] = db.run {
    universityTableQuery.to[List].result
  }


  def delete(id: Int) = db.run {
    universityTableQuery.filter(_.id === id).delete
  }

}

private[repository] trait UniversityTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  protected val universityTableQuery = TableQuery[UniversityTable]
  lazy protected val universityTableQueryInc = universityTableQuery returning universityTableQuery.map(_.id)

  //  protected def bankTableAutoInc = bankTableQuery returning bankTableQuery.map(_.id)

  private[UniversityTable] class UniversityTable(tag: Tag) extends Table[University](tag, "university") {
    val id:Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name: Rep[String] = column[String]("university_name")
    val location: Rep[String] = column[String]("location")

    def * = (id, name, location).mapTo[University]

  }

}