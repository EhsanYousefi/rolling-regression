package com.fyber.challenge.rollingregression

import com.fyber.challenge.rollingregression.datatypes._
import com.fyber.challenge.rollingregression.core.RollingRegression
import com.fyber.challenge.rollingregression.io.{SOSink, FileSource}

object Entry {
  def main(args: Array[String]): Unit = {

    val filePath: String = args(0)
    val size: Size = args.lift(1) match {
      case Some(s) => Size(s.toInt)
      case None => Size(10)
    }
    val source: FileSource = FileSource(filePath)

    RollingRegression(Config(source, Seconds(60), size, Seq(SOSink))).iterate()

  }
}