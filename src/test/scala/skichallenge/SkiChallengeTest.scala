package skichallenge

import org.scalatest.{FunSuite, BeforeAndAfter}
import scala.io.Source


class SkiChallengeTest extends FunSuite with BeforeAndAfter {
  var source: Source = _

  after { source.close }

  test("1 - Read map from String source") {
    source = Source.fromString("4 4\n4 8 7 3\n2 5 9 3\n6 3 2 5\n4 4 1 6")
    val (rows, columns, resort) = SkiChallenge.getMapFromSource(source)

    assert(rows == 4)
    assert(columns == 4)
    assert(resort === List(List(4,8,7,3),
                           List(2,5,9,3),
                           List(6,3,2,5),
                           List(4,4,1,6)))
  }

  test("1 - Read map from File") {
    val filePath = getClass.getResource("/4x4.txt").getPath
    val (rows, columns, resort) = SkiChallenge.getMapFromFile(filePath)

    assert(rows == 4)
    assert(columns == 4)
    assert(resort === List(List(4,8,7,3),
      List(2,5,9,3),
      List(6,3,2,5),
      List(4,4,1,6)))
  }
}


