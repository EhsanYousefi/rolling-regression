package io.helpers

import com.fyber.challenge.rollingregression.datatypes._
import org.scalacheck.{Arbitrary, Gen}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
object Generator {

  val size: Gen[Size] =
    for {
      value <- Arbitrary.arbitrary[Int]
    } yield Size(value)

  val priceRatio: Gen[PriceRatio] =
    for {
      value <- Arbitrary.arbitrary[Double]
    } yield PriceRatio(value)

  val timestamp: Gen[Timestamp] =
    for {
      value <- Arbitrary.arbitrary[Long]
    } yield Timestamp(value)

  val measurement: Gen[Measurement] =
    for {
      time <- timestamp
      ratio <- priceRatio
    } yield Measurement(time, ratio)

  val result: Gen[Result] =
    for {
      min <- priceRatio
      max <- priceRatio
      sum <- priceRatio
      size <- size
      measurement <- measurement
    } yield Result(min, max, sum, size, measurement)

  val resultContainer: Gen[ResultContainer] =
    for {
      results <- Gen.nonEmptyContainerOf[Vector, Result](result)
    } yield ResultContainer(results)
}
