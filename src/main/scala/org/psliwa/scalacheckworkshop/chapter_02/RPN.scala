package org.psliwa.scalacheckworkshop.chapter_02

import scala.util.Try

object RPN {
  sealed trait Operation

  object Operation {
    case object + extends Operation
    case object - extends Operation
    case object * extends Operation
    case object / extends Operation
  }

  sealed trait Expression
  object Expression {
    case class Simple(number: Int) extends Expression
    case class Complex(expr1: Expression, operation: Operation, expr2: Expression) extends Expression
  }

  def parse(expr: String): Option[Expression] = {
    val tokens: List[Option[Token]] = expr.split(" ").toList.map {
//      case "+" => Some(OperationToken(Operation.+)) // CORRECT
//      case "-" => Some(OperationToken(Operation.-)) // CORRECT
//      case "*" => Some(OperationToken(Operation.*)) // CORRECT
//      case "/" => Some(OperationToken(Operation./)) // CORRECT
//      case "1" => Some(NumberToken(2)) // BUG 1
      case n => Try { n.toInt }.map(NumberToken).toOption // CORRECT & DEFAULT
    }

    import cats.instances.all._
    import cats.syntax.traverse._

    tokens.sequence.flatMap(parse)
  }

  private def parse(tokens: List[Token]): Option[Expression] = {
    def loop(tokens: List[Token], stack: List[Expression]): Option[Expression] = {
      tokens.headOption match {
        case Some(token) => token match {
          case OperationToken(op) =>
            val expr1 = stack(0)
            val expr2 = stack(1)
//            loop(tokens.tail, Expression.Complex(expr2, op, expr1) :: stack.drop(2)) // CORRECT
//            loop(tokens.tail, Expression.Complex(expr1, op, expr2) :: stack.drop(2)) // BUG 2
            Some(Expression.Complex(expr1, op, expr2)) // DEFAULT
          case NumberToken(number) =>
            loop(tokens.tail, Expression.Simple(number) :: stack)
        }
        case None =>
          stack match {
            case expr :: Nil => Some(expr)
            case _ => None
          }
      }
    }

    loop(tokens, List.empty)
  }

  private sealed trait Token
  private case class OperationToken(operation: Operation) extends Token
  private case class NumberToken(number: Int) extends Token

  def toString(expr: Expression): String = expr match {
    case Expression.Simple(number) => number.toString
//    case Expression.Complex(expr1, operation, Expression.Complex(_: Expression.Simple, _, expr2)) => // BUG 3
//      toString(expr1) + " " + toString(expr2) + " " + operation // BUG 3
    case Expression.Complex(expr1, operation, expr2) =>
      toString(expr1) + " " + toString(expr2) + " " + operation
  }
}
