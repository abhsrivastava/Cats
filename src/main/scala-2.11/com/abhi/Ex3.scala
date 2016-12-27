package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */
import cats.Eq
import cats.instances.string._
import cats.instances.int._
import cats.instances.option._
import cats.syntax.eq._
object Ex3 extends App {
   implicit val eqPerson = Eq.instance[Person]{(p1,p2) => p1.firstName === p2.firstName && p1.lastName === p2.lastName}
   val x = 10
   val y = "foo"
   // scala
   if (x != y) println("dangerous")
   // cats. provides typesafe checks
   // you cannot do if (x =!= y) like above. compiler generates error
   if (y =!= "bar") println("Strings are not equal")
   if (x =!= 30) println("ints are not equal")
   if (Option(10) != Option("foo")) println("dangerous")
   // we cannot do Option(10) =!= Option("foo") like above
   if (Option(10) === Option(10)) println("safe comparison")
   // custom types
   val p1 = Person("Abhishek", "Srivastava")
   val p2 = Person("Mohita", "Srivastava")
   if (p1 =!= p2) println("Abhishek is not mohita")
   if (Option(p1) =!= Option.empty[Person]) println("p1 is not empty")
}
