package com.abhi

/**
  * Created by abhsrivastava on 12/25/16.
  */

trait Printable[A] {
   def format(value: A) : String
   def contramap[B](f : B => A) : Printable[B] = {
      val self = this
      new Printable[B] {
         def format(value: B) : String = {
            self.format(f(value))
         }
      }
   }
}
object Printable {
   implicit val stringPrintable = new Printable[String] {
      def format(value: String) : String = {
         s""""${value}""""
      }
   }
   implicit val booleanPrintable = new Printable[Boolean] {
      def format(value: Boolean) : String = {
         if (value) "\"true\"" else "\"false\""
      }
   }
   implicit def boxPrintable[A](implicit p : Printable[A]) = {
      p.contramap[Box[A]](x => x.value)
   }
}
final case class Box[A](value: A)

object Ex6 extends App {
   import Printable._
   def format[A](value: A)(implicit p: Printable[A]) = p.format(value)
   println(format("foo"))
   println(format(true))
   println(format(Box("Hello World")))
   println(format(Box(true)))
}
