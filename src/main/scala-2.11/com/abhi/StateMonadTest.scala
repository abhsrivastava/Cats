package com.abhi

/**
  * Created by abhsrivastava on 1/21/17.
  */

import cats.data.State
import cats.syntax.applicative._

object StateMonadTest extends App {
   type CalcState[A] = State[List[Int], A]
   def evalOne(x: String) : CalcState[Int] = {
      x match {
         case "+" => operation(_ + _)
         case "-" => operation(_ - _)
         case "*" => operation(_ * _)
         case "/" => operation(_ / _)
         case num => operator(num.toInt)
      }
   }
   def operation(f: (Int,Int)=>Int) : CalcState[Int] = {
      State[List[Int], Int] {
         case a::b::tail =>
            val output = f(a, b)
            (output::tail, output)
         case _ => (List(), 0)
      }
   }
   def operator(x: Int) : CalcState[Int] = {
      State[List[Int], Int] { stack =>
         (x::stack, x)
      }
   }
   val compute = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      _ <- evalOne("+")
      _ <- evalOne("4")
      ans <- evalOne("+")
   } yield ans
   println(compute.runA(Nil).value)
}
