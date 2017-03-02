package com.fyber.challenge.rollingregression.io

import com.fyber.challenge.rollingregression.datatypes._
import scala.util.parsing.combinator.JavaTokenParsers

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
trait MeasurementParser extends JavaTokenParsers {

  private def timestamp: Parser[Timestamp] =
    wholeNumber ^^ ( n => Timestamp(n.toInt) )
  private def priceRatio: Parser[PriceRatio] =
    floatingPointNumber ^^ ( p => PriceRatio(p.toDouble) )
  private  def measurement: Parser[Measurement] =
   timestamp ~ priceRatio ^^ {
     case time ~ ratio => Measurement(time, ratio)
   }

  protected def parseMeasurement(input: String): Measurement =
    parse(measurement, input) match {
      case Success(result, _) => result
      case NoSuccess(cause, _) => throw new RuntimeException(cause)
    }

}