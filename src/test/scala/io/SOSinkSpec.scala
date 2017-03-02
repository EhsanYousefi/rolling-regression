package io

import java.io.ByteArrayOutputStream

import com.fyber.challenge.rollingregression.datatypes._
import com.fyber.challenge.rollingregression.io.SOSink
import org.scalatest.{Matchers, FunSpec}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class SOSinkSpec extends FunSpec with Matchers {

  val computationResults: ResultContainer =
    ResultContainer {
      Vector(
        Result(PriceRatio(1.80215),PriceRatio(1.80215), PriceRatio(1.80215), Size(1), Measurement(Timestamp(1355270609), PriceRatio(1.80215))),
        Result(PriceRatio(1.80185),PriceRatio(1.80215), PriceRatio(3.604), Size(2), Measurement(Timestamp(1355270621), PriceRatio(1.80185))  )
      )
    }

  it("should print computation results") {
    val stream = new ByteArrayOutputStream()

    Console.withOut(stream) {
      SOSink.put(computationResults)
    }

    val streamAsString: String = stream.toString
    val expectedString: String =
      """T V N RS MaxV MinV 1355270609 1.80215 1 1.80215 1.80215 1.80215 1355270621 1.80185 2 3.604 1.80215 1.80185"""

    expectedString.split(" ").map(el => streamAsString.contains(el) shouldBe true)
  }

}
