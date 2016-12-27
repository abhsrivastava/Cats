package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */

sealed trait Json
final case class JsonObject (get: Map[String, String]) extends Json
final case class JsonString (get: String) extends Json
final case class JsonNumber (get: Double) extends Json

trait JsonWriter[A] {
   def write(a: A) : Json
}

final case class Person(firstName: String, lastName: String)
object JsonWriterInstance {
   implicit val stringJsonWriter = new JsonWriter[String] {
      def write(a: String) : Json = JsonString(a)
   }
   implicit val personJsonWriter = new JsonWriter[Person] {
      def write(p : Person) : Json = JsonObject(Map("firstname" -> p.firstName, "lastname" -> p.lastName))
   }
}
object Json {
   implicit class JsonOps[A](value: A) {
      def toJson(implicit w : JsonWriter[A]) : Json = {
         w.write(value)
      }
   }
}

object Ex1 extends App {
   import JsonWriterInstance._
   import Json._
   val p = Person("Abhishek", "Srivatava")
   println(p.toJson)
}
