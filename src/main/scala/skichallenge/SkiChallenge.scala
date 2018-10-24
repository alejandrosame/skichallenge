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

}
