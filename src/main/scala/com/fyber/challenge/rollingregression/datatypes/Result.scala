package com.fyber.challenge.rollingregression.datatypes

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Result(min: PriceRatio, max: PriceRatio, sum: PriceRatio, size: Size, last: Measurement)
case class ResultContainer(items: Vector[Result])