package com.abhi

/**
  * Created by abhsrivastava on 12/30/16.
  */
import cats.data.EitherT
import cats.data.OptionT
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._

object Ex13 extends App {
   type Response[A] = EitherT[Future, String, A]
   val powerLevels = Map (
      "Bumblebee" -> 6,
      "Jazz" -> 8,
      "Hot Rod" -> 12
   )

   println(tacticalReport("Hot Rod", "Jazz"))
   println(tacticalReport("Bumblebee", "Jazz"))
   println(tacticalReport("Bumblebee", "Foo"))

   def tacticalReport(ally1: String, ally2: String) : String = {
      Await.result(canSpecialMove(ally1, ally2).value, Duration.Inf) match {
         case Right(true) => s"$ally1 and $ally2 are reacy to roll"
         case Right(false) => s"$ally1 and $ally2 need a recharge"
         case Left(msg) => s"Failed $msg"
      }
   }
   def canSpecialMove(ally1: String, ally2: String) : Response[Boolean] = {
      for {
         ally1Power <- getPowerLevel(ally1)
         ally2Power <- getPowerLevel(ally2)
      } yield (ally1Power + ally2Power) > 15
   }

   def getPowerLevel(name: String) : Response[Int] = {
      powerLevels.get(name) match {
         case Some(l) => EitherT.right(Future(l))
         case None => EitherT.left(Future(s"Could not locale $name"))
      }
   }
}
