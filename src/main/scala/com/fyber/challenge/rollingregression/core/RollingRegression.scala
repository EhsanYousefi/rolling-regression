package com.fyber.challenge.rollingregression.core

import com.fyber.challenge.rollingregression.datatypes._
import com.fyber.challenge.rollingregression.io.Consumer
import scala.annotation.tailrec
import scala.collection.parallel.immutable.ParVector
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class RollingRegression(config: Config) {

  private val consumer: Consumer = Consumer(config.queueSize, config.sinks)
  private def next: Option[Measurement] = config.source.next
  private def execute(window: Window): Unit = consumer.put(Future(compute(window)))

  def iterate(): Unit =
    next.foreach { measurement =>
      Process(Window(ParVector(measurement)))
      consumer.write()
    }

  private def compute(window: Window): Result =
    window.items.foldLeft(Window.toComputationResult(window)) { (acc, m) =>
      acc.copy(
        min = PriceRatio(math.min(acc.min.value, m.priceRation.value)),
        max = PriceRatio(math.max(acc.max.value, m.priceRation.value)),
        sum = PriceRatio(acc.sum.value + m.priceRation.value),
        last = m
      )
    }

  private object Process {

    @tailrec
    def apply(window: Window): Unit = {
      execute(window)
      next match {
        case Some(m) =>
          apply(shift(window, m))
        case None =>
      }
    }

    private def shift(
       window: Window,
       measurement: Measurement
    ): Window = {
      @tailrec
      def loop(window: Window): Window = {
        val diff: Seconds = measurement.timestamp.diff(window.items.head.timestamp)
        if (diff.value <= config.length.value)
          window.copy(window.items :+ measurement)
        else if (window.items.size == 1)
          Window(ParVector(measurement))
        else loop(window.copy(window.items.tail))
      }

      loop(window)
    }
  }
}