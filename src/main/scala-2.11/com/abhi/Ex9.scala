package com.abhi

/**
  * Created by abhsrivastava on 12/29/16.
  */
import cats.data.State
import State._
object Ex9 extends App {
   val a = State[Int, String] { state =>
      (state, s"The value of state is $state")
   }
   val (state, result) = a.run(10).value
   println(state)
   println(result)
   val x = for {
      a <- get[Int]
      _ <- set[Int](a + 1)
      b <- get[Int]
      _ <- modify[Int](_ + 1)
      c <- inspect[Int, Int](_ * 1000)
   } yield (b, c)
   val (r1, c1) = x.run(10).value
   println(r1)
   println(c1)
}
