package com.fyber.challenge.rollingregression.io

import java.text.DecimalFormat

import com.fyber.challenge.rollingregression.datatypes.{ResultContainer, Result}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  * @note SO - Standard Output
  */
object SOSink extends Sink {

  private var headerHasPrinted: Boolean = false

  override def put(container: ResultContainer): Unit =
    if (headerHasPrinted) print(Formatter(container))
    else {
      println(Formatter.header)
      print(Formatter(container))
      headerHasPrinted = true
    }

  private object Formatter {

    def apply(resultContainer: ResultContainer): String =
      resultContainer.items.foldLeft("") ((acc, cr) => acc + format(cr))

    private val decimalFormatter: DecimalFormat = new DecimalFormat("#.#####")
    val header: String = {
      val titles: String =
        "%-10s %-8s %-3s %-12s %-7s %-9s" format ("T", "V", "N", "RS", "MaxV", "MinV")
      val border: String = "-" * titles.length
      titles + "\n" + border
    }

    private def roundup(value: Double): String = decimalFormatter format value

    private def format(computationResult: Result): String =
      "%d %-8s %-3d %-10s %9s %-9s".format(
        computationResult.last.timestamp.value,
        roundup(computationResult.last.priceRation.value),
        computationResult.size.value,
        roundup(computationResult.sum.value),
        roundup(computationResult.max.value),
        roundup(computationResult.min.value)
      ) + "\n"
  }
}
