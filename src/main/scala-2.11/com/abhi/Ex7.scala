package com.abhi

/**
  * Created by abhsrivastava on 12/26/16.
  */
import cats.Eval
object Ex7 extends App {
   def factorial(n: BigInt) : Eval[BigInt] = {
      if (n == 1) Eval.now(n) else Eval.defer(factorial(n - 1).map(_ * n))
   }
   val n = 50000
   println(s"factorial of ${n} is ${factorial(n).value}")
}
