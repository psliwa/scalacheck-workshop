package org.psliwa.scalacheckworkshop.chapter_01

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSuite, Matchers}

class ListSpec extends FunSuite with PropertyChecks with Matchers {

  test("non-empty list has head") {
    forAll { as: List[String] =>
      as.headOption.isDefined should be(as.nonEmpty)
    }
  }

  // topic: flatMap
  test("flatMap using 'pure' function doesn't change the output") {
    forAll { as: List[String] =>
      as.flatMap(a => List(a)) should be(as)
    }
  }

  // topic: configuration
  test("flatMap is associative") {
    def f(s: String): List[String] = List(s.toUpperCase, s.toLowerCase)

    def g(s: String): List[String] = List(s.reverse, s)

    forAll(SizeRange(30)) { as: List[String] =>
      as.flatMap(a => g(a).flatMap(f)) should be(as.flatMap(g).flatMap(f))
    }
  }

  // topic: func generators
  test("flatMap is associative (f & g generated)") {
    forAll(SizeRange(30)) { (as: List[String], f: String => List[String], g: String => List[String]) =>
      as.flatMap(a => g(a).flatMap(f)) should be(as.flatMap(g).flatMap(f))
    }
  }

}
