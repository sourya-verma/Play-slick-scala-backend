package route

import models.{Student, University}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import utils.JsonFormat._

import java.sql.Date

class RouteSpec extends PlaySpec with GuiceOneAppPerSuite {

  "Routes" should {

    "UniversityRoute: get university list" in new WithApplication {
      val Some(result) = route(app, FakeRequest(GET, "/university/list"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """[{"id":101,"name":"HCU","location":"Hyderabad"},{"id":102,"name":"JNU","location":"Delhi"},{"id":103,"name":"DU","location":"Delhi"}]"""
    }

    "UniversityRoute: create University" in new WithApplication() {
      val university = University(104, "PSG", "Tamil")
      val Some(result) = route(app, FakeRequest(POST, "/university/create").withBody(Json.toJson(university)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":104}"""

    }

    "UniversityRoute: update university" in new WithApplication() {
      val emp = University(101, "HCU", "Hyderabad")
      val Some(result) = route(app, FakeRequest(PUT, "/university/update").withBody(Json.toJson(emp)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":1}"""

    }

    "UniversityRoute: delete university" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(DELETE, "/university/delete/101"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":1}"""
    }

    "UniversityRoute: get university by id" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(GET, "/university/getby/101"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":101,"name":"HCU","location":"Hyderabad"}"""
    }

    //-----------------------------------------------------------------------------------------------------------
    "StudentRoute: get Student list" in new WithApplication {
      val Some(result) = route(app, FakeRequest(GET, "/student/list"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """[{"name":"Bob","email":"bob@xyz.com","universityId":101,"dob":660940200000,"id":1},{"name":"Rob","email":"rob@xyz.com","universityId":102,"dob":689711400000,"id":2}]"""
    }

    "StudentRoute: create Student" in new WithApplication() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      val Some(result) = route(app, FakeRequest(POST, "/student/create").withBody(Json.toJson(student)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"name":"Bob","email":"bob@xyz.com","universityId":101,"dob":660940200000,"id":1}"""

    }



    "StudentRoute: update Student" in new WithApplication() {
      val student = Student("Bob", "bob@xyz.com",101,Date.valueOf("1990-12-12"),Some(1))
      val Some(result) = route(app, FakeRequest(PUT, "/student/update").withBody(Json.toJson(student)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":1}"""

    }

    "StudentRoute: delete Student" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(DELETE, "/student/delete/1"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"id":1}"""
    }

    "StudentRoute: get Student by id" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(GET, "/student/getby/1"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsString(result) mustBe """{"name":"Bob","email":"bob@xyz.com","universityId":101,"dob":660940200000,"id":1}"""
    }
  }

}
