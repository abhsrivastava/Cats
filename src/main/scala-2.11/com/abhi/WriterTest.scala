package com.abhi

/**
  * Created by abhsrivastava on 1/21/17.
  */

import cats.data.Writer
import cats.syntax.applicative._
import cats.syntax.writer._
import cats.instances.vector._

object WriterTest extends App {
   type Logged2[A] = Writer[Vector[String], A]

   val calc1 = for {
      _ <- Vector("Started the program").tell
      a <- calculate()
   } yield a

   val (log, sum) = calc1.run
   println(log)
   println(sum)
   val (log2, fact) = factorial(5).run
   println(log2)
   println(fact)

   def calculate() : Logged2[Int] = {
      Vector("inside calculate").tell
      val output1 = calculate1(10)
      val foo = new Foo()
      val output2 = foo.calculate2(20)
      (output1 + output2).pure[Logged2]
   }

   def calculate1(x : Int) : Int = {
      Vector("came inside calculate1").tell
      val output = 10 + x
      Vector(s"Calculated value ${output}").tell
      output
   }

   def factorial(x : Int) : Logged2[Int] = {
      if (x == 1) 1.pure[Logged2]
      else {
         for {
            a <- factorial(x - 1)
            _ <- Vector(s"factorial $x is ${a*x}").tell
         } yield x * a
      }
   }
}

class Foo {
   def calculate2(x: Int) : Int = {
      Vector("came inside calculate 2").tell
      val output = 10 + x
      Vector(s"calculated ${output}").tell
      output
   }
}
