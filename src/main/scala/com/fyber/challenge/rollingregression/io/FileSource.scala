package com.fyber.challenge.rollingregression.io

import com.fyber.challenge.rollingregression.datatypes._
import scala.io.Source

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class FileSource(private val filePath: String) extends IterativeSource with MeasurementParser {

  private val file = Source.fromFile(filePath)

  override def next: Option[Measurement] = FSIterator.next

  private object FSIterator {
    private val lines = file.getLines.buffered

    def next: Option[Measurement] =
      if (lines.hasNext) Some(parseMeasurement(lines.next))
      else None
  }

}