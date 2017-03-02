import com.fyber.challenge.rollingregression.core.RollingRegression
import com.fyber.challenge.rollingregression.datatypes._
import com.fyber.challenge.rollingregression.io.{SOSink, FileSource}
import io.helpers.TestSink
import org.scalatest._

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class RollingRegressionSpec extends FunSpec with Matchers {

  it("should compute the local info within rolling window of time 60") {
    val filePath: String = getClass.getResource("/valid_dataset.txt").getPath
    val sink: TestSink = new TestSink
    val source: FileSource = FileSource(filePath)
    val expectedResult: Vector[Result] = Vector(
      Result(PriceRatio(1.80215),PriceRatio(1.80215),PriceRatio(1.80215),Size(1),Measurement(Timestamp(1355270609),PriceRatio(1.80215))),
      Result(PriceRatio(1.80185),PriceRatio(1.80215),PriceRatio(3.604),Size(2),Measurement(Timestamp(1355270621),PriceRatio(1.80185))),
      Result(PriceRatio(1.80185),PriceRatio(1.80215),PriceRatio(5.40595),Size(3),Measurement(Timestamp(1355270646),PriceRatio(1.80195))),
      Result(PriceRatio(1.80195),PriceRatio(1.80225),PriceRatio(3.6041999999999996),Size(2),Measurement(Timestamp(1355270702),PriceRatio(1.80225))),
      Result(PriceRatio(1.80195),PriceRatio(1.80225),PriceRatio(5.40635),Size(3),Measurement(Timestamp(1355270702),PriceRatio(1.80215))),
      Result(PriceRatio(1.80235),PriceRatio(1.80235),PriceRatio(1.80235),Size(1),Measurement(Timestamp(1355270829),PriceRatio(1.80235))),
      Result(PriceRatio(1.80205),PriceRatio(1.80235),PriceRatio(3.6044),Size(2),Measurement(Timestamp(1355270854),PriceRatio(1.80205))),
      Result(PriceRatio(1.80205),PriceRatio(1.80235),PriceRatio(5.40665),Size(3),Measurement(Timestamp(1355270868),PriceRatio(1.80225))),
      Result(PriceRatio(1.80245),PriceRatio(1.80245),PriceRatio(1.80245),Size(1),Measurement(Timestamp(1355271000),PriceRatio(1.80245))),
      Result(PriceRatio(1.80245),PriceRatio(1.80285),PriceRatio(3.6053),Size(2),Measurement(Timestamp(1355271023),PriceRatio(1.80285))),
      Result(PriceRatio(1.80245),PriceRatio(1.80285),PriceRatio(5.40805),Size(3),Measurement(Timestamp(1355271024),PriceRatio(1.80275))),
      Result(PriceRatio(1.80245),PriceRatio(1.80285),PriceRatio(7.2109000000000005),Size(4),Measurement(Timestamp(1355271026),PriceRatio(1.80285))),
      Result(PriceRatio(1.80245),PriceRatio(1.80285),PriceRatio(9.01355),Size(5),Measurement(Timestamp(1355271027),PriceRatio(1.80265))),
      Result(PriceRatio(1.80245),PriceRatio(1.80285),PriceRatio(10.8163),Size(6),Measurement(Timestamp(1355271056),PriceRatio(1.80275))),
      Result(PriceRatio(1.80265),PriceRatio(1.80265),PriceRatio(1.80265),Size(1),Measurement(Timestamp(1355271428),PriceRatio(1.80265))),
      Result(PriceRatio(1.80265),PriceRatio(1.80275),PriceRatio(3.6054000000000004),Size(2),Measurement(Timestamp(1355271466),PriceRatio(1.80275))),
      Result(PriceRatio(1.80265),PriceRatio(1.80295),PriceRatio(5.40835),Size(3),Measurement(Timestamp(1355271471),PriceRatio(1.80295))),
      Result(PriceRatio(1.80265),PriceRatio(1.80295),PriceRatio(5.40835),Size(3),Measurement(Timestamp(1355271507),PriceRatio(1.80265))),
      Result(PriceRatio(1.80265),PriceRatio(1.80275),PriceRatio(3.6054000000000004),Size(2),Measurement(Timestamp(1355271562),PriceRatio(1.80275))),
      Result(PriceRatio(1.80275),PriceRatio(1.80295),PriceRatio(3.6057),Size(2),Measurement(Timestamp(1355271588),PriceRatio(1.80295))))

    RollingRegression(Config(source, Seconds(60), Size(10), Seq(sink))).iterate()
    sink.queue.flatMap(_.items) should equal(expectedResult)
  }

  it("should raise runtime error if source contains invalid measurement") {
    val filePath: String = getClass.getResource("/invalid_dataset.txt").getPath
    val sink: TestSink = new TestSink
    val source: FileSource = FileSource(filePath)

    assertThrows[RuntimeException] {
      RollingRegression(Config(source, Seconds(60), Size(10), Seq(sink))).iterate()
    }
  }

  it("should feed sink 'till it reaches invalid measurement") {
    val filePath: String = getClass.getResource("/invalid_dataset.txt").getPath
    val sink: TestSink = new TestSink
    val source: FileSource = FileSource(filePath)

    assertThrows[RuntimeException] {
      RollingRegression(Config(source, Seconds(60), Size(1), Seq(sink))).iterate()
    }

    sink.queue should not be empty
  }

}