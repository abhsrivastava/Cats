package com.abhi

/**
  * Created by abhsrivastava on 12/26/16.
  */
import cats.data.Writer
import cats.syntax.writer._
import cats.instances.vector._
import cats.syntax.applicative._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Ex8 extends App {
   type Logged[A] = Writer[Vector[String], A]
   def slowly[A](f : => A): A = {
      try f finally Thread.sleep(100)
   }
   def factorial(n: Int) : Logged[Int] = {
      if (n == 1) 1.pure[Logged]
      else {
         for {
            a <- slowly(factorial(n - 1))
            _ <- Vector(s"factorial $n ${a * n}").tell
         } yield a * n
      }
   }
   val facSeq = Future.sequence(Vector(Future(factorial(10).run), Future(factorial(10).run)))
   val results = Await.result(facSeq, Duration.Inf)
   results.foreach{case (log, ans) =>
      println(log)
      println(ans)
   }
}
