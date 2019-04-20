package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.{Expression, Operation}
import org.scalacheck.{Arbitrary, Gen}

object RPNGen {

  private val operationGen: Gen[Operation] = Gen.oneOf(List(Operation.+, Operation.-, Operation.*, Operation./))

  implicit val operationArbitrary = Arbitrary(operationGen)

  private val simpleExprGen: Gen[Expression.Simple] = Gen.chooseNum(-100, 100).map(Expression.Simple)

  private def complexExprGen(maxDepth: Int): Gen[Expression.Complex] = for {
    expr1 <- exprGen(maxDepth - 1)
    expr2 <- exprGen(maxDepth - 1)
    op <- operationGen
  } yield Expression.Complex(expr1, op, expr2)

  private def exprGen(maxDepth: Int): Gen[Expression] = {
    if(maxDepth == 0) simpleExprGen
    else Gen.frequency((1, simpleExprGen), (5, complexExprGen(maxDepth)))
  }

  implicit val expressionArbitrary = Arbitrary(Gen.sized(exprGen))

}
