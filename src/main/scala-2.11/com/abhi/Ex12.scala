package com.abhi

/**
  * Created by abhsrivastava on 12/30/16.
  */
import cats.data.OptionT
import cats.instances.option._
import cats.data.Writer
import cats.implicits._

object Ex12 extends App {
   // logged type for type A and a list of strings containing debug messages
   type Logged[A] = Writer[List[String], A]
   def parseNumber(str: String) : Logged[Option[Int]] = {
      util.Try(str.toInt).toOption match {
         case Some(x) => Writer(List(s"parsed successfully $str"), Some(x))
         case None => Writer(List(s"Could not parse $str"), None)
      }
   }
   def addNumber(a: String, b: String, c: String) : Logged[Option[Int]] = {
      val result = for {
         a1 <- OptionT(parseNumber(a))
         b1 <- OptionT(parseNumber(b))
         c1 <- OptionT(parseNumber(c))
      } yield a1 + b1 + c1
      result.value
   }
   val result1 = addNumber("1", "2", "3")
   val result2 = addNumber("1", "a", "3")
   println(result1)
   println(result2)
}
