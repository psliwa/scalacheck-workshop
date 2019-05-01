package org.psliwa.scalacheckworkshop

import org.scalacheck.{Arbitrary, Gen, Shrink}
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.PropertyChecks

trait Examples extends FunSuite with PropertyChecks with Matchers {

  class SomeValue

  object RunningTests {
    test("test1") {
      forAll(SizeRange(100)) { int: Int =>
        // int is max 100
      }
    }

    test("test2") {
      forAll(SizeRange(100)) { string: String =>
        // string's max length is 100
      }
    }

    test("test3") {
      forAll(SizeRange(100)) { list: List[Int] =>
        // list's max length is 100 and max possible value is 100
      }
    }

    test("test4") {
      forAll(Gen.const(5)) { int: Int =>
        // int will be always 5 - this is example of explicit generators
      }
    }

    test("test5") {
      forAll { int: Int =>
        whenever(int % 2 == 0) {
          //drops odd numbers and tries to generate replacement for dropped values
          //if you use "if" statement, then replacement won't be generated and assertion could not be executed at all and test pass
        }
      }
    }
  }

  object Generators {
    Gen.oneOf(1, 2, 3) // generates one of provided values
    Gen.oneOf(List(1, 2, 3)) // equivalent to above
    Gen.oneOf(Gen.oneOf(1, 2), Gen.oneOf(3, 4)) // use one of provided generators to generate the value
    Gen.const(5) // generates always 5
    Gen.frequency((5, "Its weight is 5 so will be 5 times more frequently generated"), (1, "Its weight is 1")) // similar to "oneOf" but to all values have weight
    Gen.chooseNum(0, 10) // generates number from the given range
    Gen.pick(5, List(1, 2, 3, 4, 5, 6, 7)) // generates a list of size 5 with 5 elements of list from the second argument.
    Gen.size // generates the size used in given test
    Gen.sized(size => Gen.chooseNum(0, size)) // utility function that allows to create "size aware" custom generators

    val someValueGen: Gen[SomeValue] = Gen.const(new SomeValue)
    implicit val implicitGenerator: Arbitrary[SomeValue] = Arbitrary(someValueGen) // we can import this implicit in order to skip passing generator directly to forAll

    forAll { value: SomeValue =>
      // implicit the usage
    }

    forAll(someValueGen) { value: SomeValue =>
      // explicit usage
    }
  }

  object Shrinkers {
    Shrink.shrink(5) // shrinkes given value (uses implicit shrinker)
    implicit val someShrinker: Shrink[SomeValue] = Shrink { someValueToShring => Stream.empty } // a way to create custom shrinker, it needs to be imported to test
  }

}
