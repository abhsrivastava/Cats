package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */
import cats.Monoid
import cats.instances.all._
import cats.syntax.semigroup._

object Ex4 extends App{
   val map1 = Map(1 -> 1, 2 -> 2)
   val map2 = Map(3 -> 3, 4 -> 4)
   val map3 = map1 |+| map2
   println(map3)
   val opt1 = Option(1)
   val opt2 = Option(2)
   println(opt1 |+| opt2)
   val list1 = List(1, 2, 3)
   val list2 = List(4, 5, 6)
   println(list1 |+| list2)
}
