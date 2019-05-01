package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.{Expression, Operation}
import org.scalacheck.{Arbitrary, Gen}

object RPNGen {

  private val operationGen: Gen[Operation] = ???

  implicit val operationArbitrary = Arbitrary(operationGen)

  private def simpleExprGen: Gen[Expression.Simple] = ???

  private def complexExprGen(maxDepth: Int): Gen[Expression.Complex] = ???

  private def exprGen(maxDepth: Int): Gen[Expression] = ???

//  implicit val expressionArbitrary: Arbitrary[Expression] = Arbitrary(???)

}
