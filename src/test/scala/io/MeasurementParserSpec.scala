package io

import com.fyber.challenge.rollingregression.io.MeasurementParser
import org.scalatest.{FunSpec, Matchers}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class MeasurementParserSpec extends FunSpec with Matchers {

  object MockMeasurementParser extends MeasurementParser {
    def parse(input: String) = parseMeasurement(input)
  }

  it ("should parse successfully") {
    val sample: String = "1355270609 1.80215"
    MockMeasurementParser.parse(sample)
  }

  it ("should fail if timestamp is not a whole number") {
    val sample: String = "wrong 1.80215"
    assertThrows[RuntimeException](MockMeasurementParser.parse(sample))
  }

  it ("should fail if price ratio is not a double") {
    val sample: String = "1355270609 wrong"
    assertThrows[RuntimeException](MockMeasurementParser.parse(sample))
  }

}
