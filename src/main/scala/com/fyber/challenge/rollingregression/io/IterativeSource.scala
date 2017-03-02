package com.fyber.challenge.rollingregression.io

import com.fyber.challenge.rollingregression.datatypes.Measurement

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
trait IterativeSource {
  def next: Option[Measurement]
}
