package org.psliwa.scalacheckworkshop.chapter_01

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSuite, Matchers}

class ListSpec extends FunSuite with PropertyChecks with Matchers {

  test("non-empty list has head") {
    forAll { as: List[String] =>
      as.headOption.isDefined should be(as.nonEmpty)
    }
  }

}
