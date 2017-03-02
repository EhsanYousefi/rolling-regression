package com.fyber.challenge.rollingregression.io


import com.fyber.challenge.rollingregression.datatypes._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
case class Consumer(queueSize: Size, sinks: Seq[Sink]) {

  private var queue: Vector[Future[Result]] = Vector()

  def put(computation: Future[Result]): Unit = {
    queue = queue :+ computation
    if (queue.size == queueSize.value) write()
  }

  def write(): Unit = {
    val items: ResultContainer = flush
    sinks.foreach(_.put(items))
  }

  private def flush: ResultContainer = {
    val results: ResultContainer = ResultContainer(Await.result(Future.sequence(queue), 1.second))
    queue = Vector()
    results
  }

}