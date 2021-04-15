package repository

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication}

class UniversityRepoSpec extends PlaySpec with GuiceOneAppPerTest {

  import models._

  "University repository" should {

    "UniversityRepo get all rows" in new WithUniversityRepository() {
      val result = await(universityRepo.getAll)
      result.length mustBe 3
      result.head.name mustBe "HCU"
    }

    "UniversityRepo: get single rows" in new WithUniversityRepository() {
      val result = await(universityRepo.getById(103))
      result.isDefined mustBe true
      result.get.name mustBe "DU"
    }

    "University: insert a row" in new WithUniversityRepository() {
      val university = University(104 ,"BHU", "Varansi")
      val empId = await(universityRepo.create(university))
      empId mustBe 104
    }


    "UniversityRepo: update a row" in new WithUniversityRepository() {
      val result = await(universityRepo.update(University(101 ,"PSG", "Varansi")))
      result mustBe 1
    }

    "UniversityRepo: delete a row" in new WithUniversityRepository() {
      val result = await(universityRepo.delete(103))
      result mustBe 1
    }
  }


}

trait WithUniversityRepository extends WithApplication with Injecting {

  val universityRepo = inject[UniversityRepo]
}
