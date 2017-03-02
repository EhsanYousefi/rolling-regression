package com.fyber.challenge.rollingregression.datatypes

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Timestamp(value: Long) extends AnyVal {

  def diff(timestamp: Timestamp): Seconds =
    Seconds(value - timestamp.value)
}

