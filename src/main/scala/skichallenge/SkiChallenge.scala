package skichallenge

import scala.io.Source

object SkiChallenge {

  /**
    *
    * @param source Map source in text format (from File or String)
    * @return A tuple (totalRows, totalColumns, resortMap)
    */
  def getMapFromSource(source: Source): (Int, Int, List[List[Int]]) = {
    val resort = source.getLines
                       .toList.par
                       .map(_.split(" ").toList.map(_.toInt))
                       .toList

    (resort.head(0), resort.head(1), resort.tail)
  }

  /**
    *
    * @param filePath Path to file in OS with the ski resot map to read
    * @return A tuple (totalRows, totalColumns, resortMap)
    */
  def getMapFromFile(filePath: String): (Int, Int, List[List[Int]]) = {

    val source = Source.fromFile(filePath)
    val out = getMapFromSource(source)

    source.close

    out
  }

  /**
    *
    * @param resort Resort elevation map as a List of Lists of Int
    * @param i1 Row of the starting map point
    * @param j1 Column of the starting map point
    * @param i2 Row of the ending map point
    * @param j2 Column of the ending map point
    * @return Return 1 when going down the slope, 0 when on a plane (including out of bounds) and -1 when going up
    */
  def slope(resort: List[List[Int]], i1: Int, j1: Int, i2: Int, j2: Int): Int = {

    try{
      if(resort(i1)(j1) > resort(i2)(j2)) 1
      else if(resort(i1)(j1) < resort(i2)(j2)) -1
      else 0
    }catch{
      case _: IndexOutOfBoundsException => 0
    }
  }

  /**
    *
    * @param resort Resort elevation map as a List of Lists of Int
    * @param i Row of the map point we want to compute neighboring slopes
    * @param j Column of the map point we want to compute neighboring slopes
    * @return List of Ints indicating the slope for each direction in the following order [West, South, East, North]
    */
  def getSlopes(resort: List[List[Int]], i: Int, j: Int): List[Int] = {
    List(slope(resort, i, j, i,   j-1),
         slope(resort, i, j, i+1, j),
         slope(resort, i, j, i,   j+1),
         slope(resort, i, j, i-1, j))
  }

  /**
    *
    * @param resort Resort elevation map as a List of Lists of Int
    * @param i Row of the map point for which we want to determine if it is a local maximum (elevation peak)
    * @param j Column of the map point for which we want to determine if it is a local maximum (elevation peak)
    * @return Boolean clarifying whether the point is a local maximum or not based on its surrounding slopes
    */
  def isLocalMaximum(resort: List[List[Int]], i: Int, j: Int): Boolean = {
    val slopes = this.getSlopes(resort, i, j)
    !slopes.exists(_ < 0) && slopes.exists(_ > 0)
  }

  /**
    *
    * @param resort Resort elevation map as a List of Lists of Int
    * @param rows Total number of rows in the elevation map
    * @param columns Total number of columns in the elevation map
    * @return
    */
  def getLocalMaxima(resort: List[List[Int]], rows: Int, columns: Int): List[(Int, Int)] = {

    def getLocalMaxima(resort: List[List[Int]], iStart: Int, iEnd: Int, jStart: Int, jEnd: Int): List[(Int, Int)] = {

      // Split into quadrants
      if(iEnd-iStart >= 4 && jEnd-jStart >= 4) {

        val iMidEnd = ((iEnd+iStart)/2.0).floor.toInt
        val jMidEnd = ((jEnd+jStart)/2.0).floor.toInt

        List((iStart, iMidEnd, jStart, jMidEnd),
             (iStart, iMidEnd, jMidEnd, jEnd),
             (iMidEnd, iEnd, jStart, jMidEnd),
             (iMidEnd, iEnd, jMidEnd, jEnd))
          .par
          .map{case (i0, i1, j0, j1) => getLocalMaxima(resort, i0, i1, j0, j1)}
          .reduce(_ ::: _)

      // Split row dimension in half
      }else if(iEnd-iStart >= 4){

        val iMidEnd = ((iEnd+iStart)/2.0).floor.toInt

        List((iStart, iMidEnd, jStart, jEnd), (iMidEnd, iEnd, jStart, jEnd))
          .par
          .map{case (i0, i1, j0, j1) => getLocalMaxima(resort, i0, i1, j0, j1)}
          .reduce(_ ::: _)

      // Split column dimension in half
      }else if(jEnd-jStart >= 4){

        val jMidEnd = ((jEnd+jStart)/2.0).floor.toInt

        List((iStart, iEnd, jStart, jMidEnd), (iStart, iEnd, jMidEnd, jEnd))
          .par
          .map{case (i0, i1, j0, j1) => getLocalMaxima(resort, i0, i1, j0, j1)}
          .reduce(_ ::: _)

      // Base case, compute sequentially all local maxima of the current slice
      }else{

        // Precompute indices
        val indices = for {
          i <- iStart until iEnd
          j <- jStart until jEnd
        } yield (i, j)

        // Return indices that contain a local maximum
        indices.filter{case (i,j) => isLocalMaximum(resort, i, j)}.toList
      }
    }

    getLocalMaxima(resort, 0, rows, 0, columns)
  }

}
