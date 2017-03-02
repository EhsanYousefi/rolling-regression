package com.fyber.challenge.rollingregression.datatypes

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Seconds(value: Long) extends AnyVal {

  def diff(timestamp: Seconds): Seconds =
    Seconds(value - timestamp.value)
}
