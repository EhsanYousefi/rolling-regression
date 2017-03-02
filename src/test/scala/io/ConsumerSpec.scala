package io

import com.fyber.challenge.rollingregression.datatypes._
import com.fyber.challenge.rollingregression.io.Consumer
import io.helpers.{TestSink, Generator}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, FunSpec}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class ConsumerSpec extends FunSpec with Matchers with GeneratorDrivenPropertyChecks {

  it("shouldn't flush if queue's bound hasn't reached") {
    val sink: TestSink = new TestSink
    val consumer: Consumer = Consumer(Size(3), Seq(sink))

    consumer.put(Future.successful(Generator.result.sample.get))
    consumer.put(Future.successful(Generator.result.sample.get))

    sink.queue.size should equal(0)
  }

  it("should flush when queue bound has reached") {
    val sink: TestSink = new TestSink
    val consumer: Consumer = Consumer(Size(3), Seq(sink))

    consumer.put(Future.successful(Generator.result.sample.get))
    consumer.put(Future.successful(Generator.result.sample.get))
    consumer.put(Future.successful(Generator.result.sample.get))

    sink.queue.size should equal(1)
  }

  it("should flush items in order they have placed") {
    val sink: TestSink = new TestSink
    val consumer: Consumer = Consumer(Size(10), Seq(sink))

    forAll(Generator.resultContainer) { container =>
      container.items foreach { result =>
        consumer put Future {
          Thread.sleep(5)
          result
        }
      }

      consumer.write()
      sink.queue.flatMap(_.items) should equal(container.items)
      sink.queue = Vector()
    }
  }

}
