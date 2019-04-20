package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.Expression
import org.scalacheck.Shrink

object RPNShrink {
  implicit val expressionShrink: Shrink[Expression] = Shrink {
    case Expression.Simple(n) =>
      Shrink.shrink(n).map(Expression.Simple)

    case Expression.Complex(expr1: Expression.Simple, op, expr2: Expression.Simple) =>
      val complexShrinked = expressionShrink.shrink(expr1).zip(expressionShrink.shrink(expr2)).map { case(e1, e2) =>
        Expression.Complex(e1, op, e2)
      }
      complexShrinked ++ Stream(expr1, expr2) ++ expressionShrink.shrink(expr1) ++ expressionShrink.shrink(expr2)

    case Expression.Complex(expr1, op, expr2) =>

      val expr1Shrink = expressionShrink.shrink(expr1)
      val expr2Shrink = expressionShrink.shrink(expr2)

      expr1Shrink.map(Expression.Complex(_, op, expr2)) ++ expr2Shrink.map(Expression.Complex(expr1, op, _)) ++
        expr1Shrink ++ expr2Shrink
  }
}
