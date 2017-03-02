package com.fyber.challenge.rollingregression.datatypes

import scala.collection.parallel.immutable.ParVector

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Window(items: ParVector[Measurement] = ParVector())

object Window {

  def toComputationResult(window: Window): Result = {
    val first: Measurement = window.items.head
    Result(
      first.priceRation, first.priceRation, PriceRatio(0), Size(window.items.size), first
    )
  }
}