package com.fyber.challenge.rollingregression.io

import com.fyber.challenge.rollingregression.datatypes.{ResultContainer, Result}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
trait Sink {
  def put(items: ResultContainer): Unit
}