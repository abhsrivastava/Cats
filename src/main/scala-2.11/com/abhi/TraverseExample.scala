package com.abhi

import scala.language.higherKinds
import cats.Applicative
import cats.instances.future._
import cats.syntax.cartesian._
import cats.syntax.applicative._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

import cats.Traverse
import cats.instances.future._
import cats.instances.list._
import cats.syntax.traverse._

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

   val f1 = Await.result(listTraverse(hostnames)(getUptime), Duration.Inf)
   println(f1)
   val f2 = Await.result(hostnames.traverse(getUptime), Duration.Inf)
   println(f2)
}
