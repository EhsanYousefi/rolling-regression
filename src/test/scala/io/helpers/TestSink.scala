package io.helpers

import com.fyber.challenge.rollingregression.datatypes.ResultContainer
import com.fyber.challenge.rollingregression.io.Sink

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class TestSink extends Sink {

  var queue: Vector[ResultContainer] = Vector()
  override def put(container: ResultContainer): Unit =
    queue = queue :+ container

}
