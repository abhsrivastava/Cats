package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */

import cats.Show
import cats.instances.all._
import cats.syntax.show._

object Ex2 extends App {
   implicit val showPerson : Show[Person] = {
      Show.show(p => s"Person's name is ${p.firstName} last name is ${p.lastName}")
   }
   val p = new Person("Abhishek", "Srivastava")
   val x = 123
   val y = "abhi"
   println(x.show)
   println(y.show)
   println(p.show)
}
