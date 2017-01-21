package com.abhi

/**
  * Created by abhsrivastava on 1/21/17.
  */
import cats.data.Reader
import cats.syntax.applicative._
object ReaderTest extends App {
   type DbReader2[A] = Reader[Map[Int, String], A]
   val input = Map(1 -> "foo")
   val output = findUser(1).run(input)
   println(output.getOrElse("not found"))
   def findUser(x: Int) : DbReader2[Option[String]] = {
      Reader(db =>
         db.get(x)
      )
   }
}

case class Db (userNames: Map[String, String], password: Map[String, String])