package org.psliwa.scalacheckworkshop.chapter_02

import org.psliwa.scalacheckworkshop.chapter_02.RPN.Expression
import org.scalacheck.Shrink

object RPNShrink {
  implicit val expressionShrink: Shrink[Expression] = Shrink.apply(_ => Stream.empty)
}
