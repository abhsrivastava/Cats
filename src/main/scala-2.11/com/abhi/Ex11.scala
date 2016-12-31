package com.abhi

/**
  * Created by abhsrivastava on 12/30/16.
  */
import cats.data.OptionT
import cats.instances.list._
import cats.syntax.applicative._
import cats.instances.either._

object Ex11 extends App {
   type ErrorEither[A] = Either[String, A]
   type ErrorOrOption[A] = OptionT[ErrorEither, A]
   val x = 42.pure[ErrorOrOption]
   println(x)
}
