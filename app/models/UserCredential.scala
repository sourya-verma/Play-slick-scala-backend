package models

import java.sql.Date

case class UserCredential(firstName:String, lastName:String, userID:String, password:String, createdDate:Date);
