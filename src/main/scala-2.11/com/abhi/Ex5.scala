package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */

import cats.instances.all._
import cats.Functor
import cats.syntax.functor._

sealed trait Tree[+A]
final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
final case class Leaf[A](value: A) extends Tree[A]

object Ex5 extends App {
   implicit val treeFunction = new Functor[Tree] {
      def map[A, B](tree: Tree[A])(f : A => B): Tree[B] =
         tree match {
            case Branch(l, r) => Branch(map(l)(f), map(r)(f))
            case Leaf(l) => Leaf(f(l))
         }
   }
   val t1 = Branch(Leaf(10), Leaf(10)).asInstanceOf[Tree[Int]]
   println(t1.map(x => "T" + x.toString))
}
