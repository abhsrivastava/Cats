package com.abhi

/**
  * Created by abhsrivastava on 12/31/16.
  */
import cats.data.Validated
import cats.Cartesian
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object Ex14 extends App {
   type ValidResponse[A] = Validated[List[String], A]
   type EitherResponse[A] = Either[List[String], A]
   case class User(name: String, age: Int)
   def getValue(name: String)(map: Map[String, String]) : EitherResponse[String] = {
      import cats.syntax.either._
      Either.fromOption(map.get(name), List(s"Could not find $name"))
   }
   def parseInt(name: String)(input: String) : EitherResponse[Int] = {
      import cats.syntax.either._
      Either
         .catchOnly[NumberFormatException](input.toInt)
         .leftMap(_ => List(s"could not parse $input for $name"))
   }
   def nonBlank(name: String)(input: String) : EitherResponse[String] = {
      import cats.syntax.either._
      Right(name).ensure(List(s"$name cannot be empty"))(_.nonEmpty)
   }

   def validRange(name: String)(input: Int) : EitherResponse[Int] = {
      (1 to 100).contains(input) match {
         case true => Right(input)
         case false => Left(List(s"$name has to be between 1 and 100"))
      }
   }
   def getName = getValue("name") _
   def readName(map: Map[String, String]) : EitherResponse[String] = {
      import cats.implicits.catsSyntaxEither
      getName(map).flatMap(nonBlank("name"))
   }
   def readAge(map: Map[String, String]) : EitherResponse[Int] = {
      import cats.implicits.catsSyntaxEither
      getValue("age")(map)
         .flatMap(nonBlank("age"))
         .flatMap(parseInt("age"))
         .flatMap(validRange("age"))
   }
   def readUser(map: Map[String, String]) : ValidResponse[User] = {
      import cats.implicits._
      Cartesian[ValidResponse].product(
         getName(map).toValidated,
         readAge(map).toValidated
      ).map(User.tupled)
   }
   val user = readUser(Map("name" -> "Foo", "age" -> "42"))
   println(user)
   val user1 = readUser(Map())
   println(user1)
   val user2 = readUser(Map("name" -> "Bar", "age" -> "-1"))
   println(user2)
}
