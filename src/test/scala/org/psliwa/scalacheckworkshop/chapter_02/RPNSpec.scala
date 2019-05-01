package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.{Expression, Operation}
import org.scalatest.prop.{PropertyChecks, Whenever}
import org.scalatest.{FunSuite, Matchers, OptionValues}
import RPNGen._
import RPNShrink.expressionShrink

class RPNSpec extends FunSuite with PropertyChecks with Matchers with Whenever with OptionValues {

  test("parse single number") {
    RPN.parse("20").value should be(Expression.Simple(20))
  }

  ignore("parse all single numbers") {
  }

  test("parse expression with one operation") {
    RPN.parse("1 2 +").value should be(Expression.Complex(Expression.Simple(1), Operation.+, Expression.Simple(2)))
  }

  ignore("parse all expressions with one operation") {
  }

  test("parse expression with two operations") {
    RPN.parse("1 2 + 3 /").value should be(
      Expression.Complex(
        Expression.Complex(Expression.Simple(1), Operation.+, Expression.Simple(2)),
        Operation./,
        Expression.Simple(3)
      )
    )
  }

  ignore("parse all expression with two operations") {
  }

  test("parse very complex expression") {
//    (2 + 7) / 3 + (14 - 3) * 4 / 2
    RPN.parse("2 7 + 3 / 14 3 - 4 * 2 / +").value should be(
      Expression.Complex(
        Expression.Complex(
          Expression.Complex(
            Expression.Simple(2),
            Operation.+,
            Expression.Simple(7)
          ),
          Operation./,
          Expression.Simple(3)
        ),
        Operation.+,
        Expression.Complex(
          Expression.Complex(
            Expression.Complex(
              Expression.Simple(14),
              Operation.-,
              Expression.Simple(3)
            ),
            Operation.*,
            Expression.Simple(4)
          ),
          Operation./,
          Expression.Simple(2)
        )
      )
    )
  }

  test("toString result is the same as parse input") {
    RPN.parse("2 7 + 3 / 14 3 - 4 * 2 / +").map(RPN.toString).value should be("2 7 + 3 / 14 3 - 4 * 2 / +")
  }

  ignore("all toString results are the same as parse input") {
  }

}
