package controllers


import models.Student
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.i18n.{DefaultLangs, MessagesApi}
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication, _}
import repository.{StudentRepo}
import utils.JsonFormat._
import org.mockito.MockitoSugar

import java.sql.Date
import scala.concurrent.{ExecutionContext, Future}


class StudentControllerSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerTest {

  implicit val mockedRepo: StudentRepo = mock[StudentRepo]


  "StudentController " should {

    "StudentController: create a Student" in new WithStudentApplication() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      when(mockedRepo.create(student)) thenReturn Future.successful(Some(1))
      val result = studentController.create().apply(FakeRequest().withBody(Json.toJson(student)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"name":"Bob","email":"bob@xyz.com","universityId":101,"dob":660940200000,"id":1}"""
    }

    "StudentController: update a student" in new WithStudentApplication() {
      val updatedStudent = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      when(mockedRepo.update(updatedStudent)) thenReturn Future.successful(1)
      val result = studentController.update().apply(FakeRequest().withBody(Json.toJson(updatedStudent)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":1}"""
    }


    "StudentController: delete a student" in new WithStudentApplication() {
      when(mockedRepo.delete(1)) thenReturn Future.successful(1)
      val result = studentController.delete(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":1}"""
    }

    "StudentController: get all list" in new WithStudentApplication() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      when(mockedRepo.getAll()) thenReturn Future.successful(List(student))
      val result = studentController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """[{"id":101,"name":"HCU","location":"Hyderabad"}]"""
    }

    "get by id " in new WithStudentApplication() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      when(mockedRepo.getById(1)) thenReturn Future.successful(Some(student))
      val result = studentController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"name":"Bob","email":"bob@xyz.com","universityId":101,"dob":660940200000,"id":1}"""
    }

    //    "get university by id" in new WithApplication() {
    //      val Some(result) = route(app, FakeRequest(GET, "/university/getby/101"))
    //      status(result) mustBe OK
    //      contentType(result) mustBe Some("application/json")
    //      contentAsString(result) mustBe """{"id":101,"name":"HCU","location":"Hyderabad"}"""
    //    }


  }

}

class WithStudentApplication(implicit mockedRepo: StudentRepo) extends WithApplication with Injecting {

  implicit val ec = inject[ExecutionContext]

  val studentController: StudentController =
    new StudentController(
      stubControllerComponents(),
      mockedRepo
    )

}
