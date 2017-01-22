package com.abhi

import cats.Applicative

import scala.language.higherKinds
import cats.syntax.all._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
/**
  * Created by abhsrivastava on 1/22/17.
  */
object TraverseExample extends App {
   def listTraverse[F[_] : Applicative, A, B](list: List[A])(f: A => F[B]) : F[List[B]] = {
      list.foldLeft(List.empty[B].pure[F]){(acc, item) =>
         (acc |@| f(item)).map(_ :+ _)
      }
   }
   def listSequence[F[_]: Applicative, B](list: List[F[B]]) : F[List[B]] = {
      listTraverse(list)(identity)
   }

   var hostnames = List("a.com", "b.com", "c.com")

   def getUptime(hostname: String) : Future[Int] = {
      Future(hostname.length * 60)
   }

   val f = Await.result(listTraverse(hostnames)(getUptime), Duration.Inf)
   println(f)
}
