package io

import com.fyber.challenge.rollingregression.io.FileSource
import org.scalatest.{Matchers, FunSpec}

/**
  * @author Ehsan Yousefi <ehsan.yousefi@live.com>
  */
class FileSourceSpec extends FunSpec with Matchers {

  val invalidSource: FileSource = FileSource(getClass.getResource("/invalid_dataset.txt").getPath)

  it("should parse file's first line successfully") {
    invalidSource.next match {
      case Some(m) =>
      case None => fail("file source didn't parse the valid line succesfully")
    }
  }

  it("should raise runtime exception while parsing file's second line") {
    assertThrows[RuntimeException](invalidSource.next)
  }

}
