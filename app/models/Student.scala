package models

import java.sql.Date

case class Student(name : String, email : String, universityId: Int, dob : Date, id: Option[Int] = None)