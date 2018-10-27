package skichallenge

object Main extends App{

  val startTime = System.currentTimeMillis

  val filePath = getClass.getResource("/map.txt").getPath

  val (rows, columns, resort) = SkiChallenge.getMapFromFile(filePath)

  val longestPath = SkiChallenge.getLongestPath(resort, rows, columns)

  val elapsedTime = System.currentTimeMillis - startTime

  println(s"Execution time: ${(elapsedTime / 1000)} s")

  println(s"\nPath length: ${longestPath.length}\n"++
          s"Path drop: ${longestPath.drop}\n"++
          s"Path values: ${longestPath.pathValues.mkString("-")}\n"++
          s"Path indices: ${longestPath.path.mkString("-")}\n")

}
