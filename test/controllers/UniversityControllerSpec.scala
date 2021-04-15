package controllers


import models.University
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.i18n.{DefaultLangs, MessagesApi}
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication, _}
import repository.{UniversityRepo}
import utils.JsonFormat._
import org.mockito.MockitoSugar

import scala.concurrent.{ExecutionContext, Future}


class UniversityControllerSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerTest {

  implicit val mockedRepo: UniversityRepo = mock[UniversityRepo]


  "UniversityController " should {

    "UniversityController: create a university" in new WithUniversityApplication() {
      val university = University(101, "HCU", "Hyderabad")
      when(mockedRepo.create(university)) thenReturn Future.successful(1)
      val result = univeristyController.create().apply(FakeRequest().withBody(Json.toJson(university)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":1}"""
    }

    "UniversityController: update a university" in new WithUniversityApplication() {
      val updatedUniversity = University(101, "UOH", "Hyderabad")
      when(mockedRepo.update(updatedUniversity)) thenReturn Future.successful(1)
      val result = univeristyController.update().apply(FakeRequest().withBody(Json.toJson(updatedUniversity)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":1}"""
    }


    "UniversityController: delete a university" in new WithUniversityApplication() {
      when(mockedRepo.delete(1)) thenReturn Future.successful(1)
      val result = univeristyController.delete(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":1}"""
    }

    "UniversityController: get all list" in new WithUniversityApplication() {
      val university = University(101, "HCU", "Hyderabad")
      when(mockedRepo.getAll()) thenReturn Future.successful(List(university))
      val result = univeristyController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """[{"id":101,"name":"HCU","location":"Hyderabad"}]"""
    }

    "UniversityController: get by id " in new WithUniversityApplication() {
      val university = University(101, "HCU", "Hyderabad")
      when(mockedRepo.getById(101)) thenReturn Future.successful(Some(university))
      val result = univeristyController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"id":101,"name":"HCU","location":"Hyderabad"}"""
    }



  }

}

class WithUniversityApplication(implicit mockedRepo: UniversityRepo) extends WithApplication with Injecting {

  implicit val ec = inject[ExecutionContext]

  val univeristyController: UniversityController =
    new UniversityController(
      stubControllerComponents(),
      mockedRepo
    )

}
