package repository

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication}

import java.sql.Date

class StudentRepoSpec extends PlaySpec with GuiceOneAppPerTest {

  import models._

  "Student repository" should {

    "StudentRepo: Student: get all rows" in new WithStudentRepository() {
      val result = await(studentRepo.getAll)
      result.length mustBe 2
      result.head.name mustBe "Bob"
    }

    "StudentRepo: Student: get single rows" in new WithStudentRepository() {
      val result = await(studentRepo.getById(1))
      result.isDefined mustBe true
      result.get.name mustBe "Bob"
    }

    "StudentRepo: Student: insert a row" in new WithStudentRepository() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      val empId = await(studentRepo.create(student))
      empId mustBe Some(3)
    }


    "StudentRepo: Student: update a row" in new WithStudentRepository() {
      val result = await(studentRepo.update(Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))))
      result mustBe 1
    }

    "StudentRepo:  delete a row" in new WithStudentRepository() {
      val result = await(studentRepo.delete(1))
      result mustBe 1
    }
  }


}

trait WithStudentRepository extends WithApplication with Injecting {

  val studentRepo = inject[StudentRepo]
}
