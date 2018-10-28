# Ski resort challenge

Implementation of the ski resort challenge in Scala.

## Requirements

* JVM 1.8 (e.g. OpenJDK 8)
* sbt ([Installation instructions](https://www.scala-sbt.org/1.x/docs/Setup.html))

## Execution

`Note: While the following instructions should be valid for any system, they where executed and
tested using Ubuntu 18.04.`

Clone this repo (or simply download the folder):

```
$ git clone https://github.com/alejandrosame/skichallenge.git
```

Move into the repo folder:

```
$ cd skichallenge
```

Execute sbt shell:

```
$ sbt
```

You should get an output like:

```
[info] Loading project definition from /home/alejandro/repositories/alejandrosame/skichallenge/project
[info] Loading settings for project skichallenge from build.sbt ...
[info] Set current project to skichallenge (in build file:/home/alejandro/repositories/alejandrosame/skichallenge/)
[info] sbt server started at local:///home/alejandro/.sbt/1.0/server/fc1beff2c4531cfba79c/sock
sbt:skichallenge>
```

Now, from the sbt shell you can compile the code:

```
sbt:skichallenge> compile
```

To execute the test suite, execute:

```
sbt:skichallenge> test

[info] SkiChallengeTest:
[info] - 1 - Read map from String source
[info] - 2 - Read map from File
[info] - 3 - Get list of slopes
[info] - 4 - Check local maxima
[info] - 5 - Find local maxima
[info] - 6 - Find downwards directions
[info] - 7 - Get longest path from specific starting point
[info] - 8 - Find longest path in the resort map
[info] Run completed in 360 milliseconds.
[info] Total number of tests run: 8
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 8, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.

```

And to run the code:

```
sbt:skichallenge> run

[info] Running skichallenge.Main
Execution time: 111 s

Path length: N
Path drop: X
Path values: v1-v2-...-vN
Path indices: (i1,j1)-(i2,j2)-...-(iN,jN)

[success] Total time: 112 s, completed Oct 27, 2018 1:08:41 PM
```

Execution time in an IDE normally takes <40s for a 1000x1000 map. As sbt introduces some different overheads
it usually executes in <120s.

## Implementation documentation

To read further about the implementation and its improvement points, read [here](src/main/scala/skichallenge).
