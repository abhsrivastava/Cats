package com.abhi

/**
  * Created by abhsrivastava on 1/21/17.
  */
import cats.data.Reader
import cats.syntax.applicative._
object ReaderTest extends App {
   type DbReader2[A] = Reader[Db, A]
   val input = Db(Map("foo" -> 1), Map(1 -> "bar"))
   val output = checkLogin("foo", "baz").run(input)
   println(output)

   def findUserId(name: String) : DbReader2[Option[Int]] = {
      Reader(db =>
         db.userNames.get(name)
      )
   }
   def checkPassword(userId: Int, password: String) : DbReader2[Boolean] = {
      Reader(db =>
         db.password.get(userId).contains(password)
      )
   }
   def checkLogin(userName: String, password: String) : DbReader2[Boolean] = {
      for {
         userId <- findUserId(userName)
         passwordOk <- userId.map(userId => checkPassword(userId, password)).getOrElse(false.pure[DbReader2])
      } yield passwordOk
   }
}

case class Db (userNames: Map[String, Int], password: Map[Int, String])