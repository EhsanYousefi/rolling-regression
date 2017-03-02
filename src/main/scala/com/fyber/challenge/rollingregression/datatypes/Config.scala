package com.fyber.challenge.rollingregression.datatypes

import com.fyber.challenge.rollingregression.io.{Sink, IterativeSource}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Config(
  source: IterativeSource,
  length: Seconds,
  queueSize: Size,
  sinks: Seq[Sink]
)