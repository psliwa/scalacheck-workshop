package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.{Expression, Operation}
import org.scalatest.prop.{PropertyChecks, Whenever}
import org.scalatest.{FunSuite, Matchers, OptionValues}
import RPNGen._

// topic: mixing traditional example based tests with property based
class RPNSpec extends FunSuite with PropertyChecks with Matchers with Whenever with OptionValues {

  test("parse single number") {
    RPN.parse("20").value should be(Expression.Simple(20))
  }

  test("parse all single numbers") {
    forAll { number: Int =>
      val string = number.toString

      RPN.parse(string).value should be(Expression.Simple(number))
    }
  }

  test("parse expression with one operation") {
    RPN.parse("1 2 +").value should be(Expression.Complex(Expression.Simple(1), Operation.+, Expression.Simple(2)))
  }

  // topic: simple custom generator
  test("parse all expressions with one operation") {
    forAll { (number1: Int, operation: Operation, number2: Int) =>
      val string = s"$number1 $number2 $operation"

      RPN.parse(string).value should be(Expression.Complex(Expression.Simple(number1), operation, Expression.Simple(number2)))
    }
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

  test("parse all expression with two operations") {
    forAll { (number1: Int, number2: Int, operation1: Operation, number3: Int, operation2: Operation) =>
      RPN.parse(s"$number1 $number2 $operation1 $number3 $operation2").value should be(
        Expression.Complex(
          Expression.Complex(Expression.Simple(number1), operation1, Expression.Simple(number2)),
          operation2,
          Expression.Simple(number3)
        )
      )
    }
  }

  test("parse very complex expression") {
    // ((2+7)/3+(14-3)*4)/2
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

  // topic: complex custom generator
  // topic: shrink
  test("all toString results are the same as parse input") {
    import RPNShrink.expressionShrink
    forAll(SizeRange(10)) { expression: Expression =>
      val parsedAgain = RPN.parse(RPN.toString(expression)).value
      RPN.toString(expression) shouldBe RPN.toString(parsedAgain)
      parsedAgain shouldBe expression
    }
  }

}
