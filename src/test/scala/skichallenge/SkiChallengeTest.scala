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

  test("2 - Read map from File") {
    val filePath = getClass.getResource("/4x4.txt").getPath
    val (rows, columns, resort) = SkiChallenge.getMapFromFile(filePath)

    assert(rows == 4)
    assert(columns == 4)
    assert(resort === List(List(4,8,7,3),
                           List(2,5,9,3),
                           List(6,3,2,5),
                           List(4,4,1,6)))
  }

  test("3 - Get list of slopes") {
    val resort = List(List(4,8,7,3),
                      List(2,5,9,3),
                      List(6,3,2,5),
                      List(4,4,1,6))

    assert(SkiChallenge.getSlopes(resort, 0, 1) === List(1, 1, 1, 0))
    assert(SkiChallenge.getSlopes(resort, 1, 2) === List(1, 1, 1, 1))
    assert(SkiChallenge.getSlopes(resort, 2, 0) === List(0, 1, 1, 1))
    assert(SkiChallenge.getSlopes(resort, 3, 1) === List(0, 0, 1, 1))
    assert(SkiChallenge.getSlopes(resort, 3, 3) === List(1, 0, 0, 1))
    assert(SkiChallenge.getSlopes(resort, 0, 0) === List(0, 1, -1, 0))
    assert(SkiChallenge.getSlopes(resort, 2, 2) === List(-1, 1, -1, -1))
    assert(SkiChallenge.getSlopes(resort, 3, 0) === List(0, 0, 0, -1))


    val resort2 = List(List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,5,8),
                       List(8,8,8,8))

    assert(SkiChallenge.getSlopes(resort2, 1, 2) === List(0, 1, 0, 0))
    assert(SkiChallenge.getSlopes(resort2, 2, 1) === List(0, 0, 1, 0))
    assert(SkiChallenge.getSlopes(resort2, 2, 3) === List(1, 0, 0, 0))
    assert(SkiChallenge.getSlopes(resort2, 3, 2) === List(0, 0, 0, 1))
    assert(SkiChallenge.getSlopes(resort2, 1, 1) === List(0, 0, 0, 0))
    assert(SkiChallenge.getSlopes(resort2, 2, 2) === List(-1, -1, -1, -1))
    assert(SkiChallenge.getSlopes(resort2, 3, 3) === List(0, 0, 0, 0))

  }


  test("4 - Check local maxima") {
    val resort = List(List(4,8,7,3),
                      List(2,5,9,3),
                      List(6,3,2,5),
                      List(4,4,1,6))

    assert(SkiChallenge.isLocalMaximum(resort, 0, 1))
    assert(SkiChallenge.isLocalMaximum(resort, 1, 2))
    assert(SkiChallenge.isLocalMaximum(resort, 2, 0))
    assert(SkiChallenge.isLocalMaximum(resort, 3, 1))
    assert(SkiChallenge.isLocalMaximum(resort, 3, 3))

    assert(!SkiChallenge.isLocalMaximum(resort, 0, 0))
    assert(!SkiChallenge.isLocalMaximum(resort, 2, 2))
    assert(!SkiChallenge.isLocalMaximum(resort, 3, 0))


    val resort2 = List(List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,5,8),
                       List(8,8,8,8))

    assert(SkiChallenge.isLocalMaximum(resort2, 1, 2))
    assert(SkiChallenge.isLocalMaximum(resort2, 2, 1))
    assert(SkiChallenge.isLocalMaximum(resort2, 2, 3))
    assert(SkiChallenge.isLocalMaximum(resort2, 3, 2))

    assert(!SkiChallenge.isLocalMaximum(resort2, 1, 1))
    assert(!SkiChallenge.isLocalMaximum(resort2, 2, 2))
    assert(!SkiChallenge.isLocalMaximum(resort2, 3, 3))


    val resort3 = List(List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,8,8))

    for(i <- 0 until 4){
      for(j <- 0 until 4){
        assert(!SkiChallenge.isLocalMaximum(resort3, i, j))
      }
    }

  }

  test("5 - Find local maxima") {

    val resort = List(List(4,8,7,3),
                      List(2,5,9,3),
                      List(6,3,2,5),
                      List(4,4,1,6))

    val resortMaxima = List((0,1), (1,2), (2,0), (3,1), (3,3))

    assert(SkiChallenge.getLocalMaxima(resort, 4, 4) === resortMaxima)


    val resort2 = List(List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,5,8),
                       List(8,8,8,8))

    val resort2Maxima = List((1,2), (2,1), (2,3), (3,2))

    assert(SkiChallenge.getLocalMaxima(resort2, 4, 4) === resort2Maxima)


    val resort3 = List(List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,8,8),
                       List(8,8,8,8))

    val resort3Maxima = List()

    assert(SkiChallenge.getLocalMaxima(resort3, 4, 4) === resort3Maxima)

    // Some more especial cases
    assert(SkiChallenge.getLocalMaxima(List(), 0, 0) === List())

    assert(SkiChallenge.getLocalMaxima(List(List(2)), 1, 1) === List())

    assert(SkiChallenge.getLocalMaxima(List(List(2,1), List(1,1)), 2, 2) === List((0,0)))

    assert(SkiChallenge.getLocalMaxima(List(List(1,2,1), List(1,1,1)), 2, 3) === List((0,1)))

    assert(SkiChallenge.getLocalMaxima(List(List(1,1,1),
                                            List(1,2,1),
                                            List(1,1,1)), 3, 3) === List((1,1)))


  }

}


