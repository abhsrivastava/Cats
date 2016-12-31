package com.abhi

/**
  * Created by abhsrivastava on 12/29/16.
  */
import cats.data.OptionT
import cats.Monad
import cats.instances.list._
import cats.syntax.applicative._
object Ex10 extends App {

   // without monad transformers
   val x1 = List(Some(42), Some(23))
   val y1 = List(Some(20))
   val z1 = x1 flatMap { a => y1 map { b => Some(a.get + b.get)}}
   println(z1)

   // with monad transformers
   type ListOption[A] = OptionT[List, A]
   val x2 = 42.pure[ListOption]
   val y2 = 20.pure[ListOption]
   val z2 = x2 flatMap { a => y2 map { b => a + b}}
   println(z2)
}
