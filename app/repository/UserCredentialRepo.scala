
package repository

import javax.inject.{Inject, Singleton}
import models.{UserCredential, LoginDetail}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.sql.Date
import scala.concurrent.Future

@Singleton()
class UserCredentialRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends UserCredentialTable with HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  def create(user: UserCredential) = db.run {
    userCredentialTableQuery += user
  }


  def update(user: UserCredential):Future[Int] = db.run {
    userCredentialTableQuery.filter(_.userID === user.userID).update(user)
  }

  def validate(user: LoginDetail) =  db.run{
      userCredentialTableQuery.filter(usr=> (usr.userID === user.userID && usr.password=== user.password)).result.headOption
  }




  def getById(userID: String) = db.run {
    userCredentialTableQuery.filter(_.userID === userID).result.headOption
  }


  def getAll() = db.run {
    userCredentialTableQuery.to[List].result
  }


  def delete(userID: String) = db.run {
    userCredentialTableQuery.filter(_.userID === userID).delete
  }

}

private[repository] trait UserCredentialTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  protected val userCredentialTableQuery = TableQuery[UserCredentialTable]
//  lazy protected val universityTableQueryInc = UserCredentialTableQuery returning UserCredentialTableQuery.map(_.userID)

  //  protected def bankTableAutoInc = bankTableQuery returning bankTableQuery.map(_.id)

  private[UserCredentialTable] class UserCredentialTable(tag: Tag) extends Table[UserCredential](tag, "user_credential") {
    val userID:Rep[String] = column[String]("user_id", O.PrimaryKey)
    val firstName: Rep[String] = column[String]("first_name")
    val lastName: Rep[String] = column[String]("last_name")
    val password: Rep[String] = column[String]("password")
    val createdDate: Rep[Date] = column[Date]("created_date")

    def * = (firstName, lastName, userID,password, createdDate).mapTo[UserCredential]

  }

}