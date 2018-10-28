# Implementation comments

## Algorithm

The algorithm implemented to solve the challenge is very simple and requires 3 steps:

1. First, we find all the maximum height coordinates in the map (peak points where a downward path starts). This is 
implemented in the method `getLocalMaxima`.
2. Then, for each local maximum, we take build all the possible downward paths and return the best candidate. This 
is implemented in the method `longestPathFromPoint`.
3. Once we get all the best candidates for each peak, we choose the best candidate. This is implemented in the 
method `getLongestPath`, which is the entry point for the algorithm.


## Improvement points for the proposed implementation

The current implementation works without problems for the 1000x1000 map provided in the challenge. However, as any
program should aim to be as generic as possible, here are presented some improvement points to make the implementation
more useful.

### Potential stack overflow problems

Line `196` in `SkiChallenge.scala` is a potential candidate for stack overflow errors if 
many starting points lead to long candidate paths, as it could overload the stack memory.

This issue, if necessary, could be fixed with the following approaches:

* **Making bigger splits for parallelization:** instead of parallelizing the build of paths for every single maximum point,
group them and run the `longestPathFromPoint` method sequentially for each member of a group.

* **Use a write-only cache for path construction:** calls for `buildPaths` methods could construct the path using some
data structure (like a table of linked lists) to avoid overloading memory in the stack call.

* **Build paths sequentially:** the method `longestPathFromPoint` could also be implemented as a sequential traversal of
the candidate paths, instead of recursively.

### Legibility improvements

The code could be more legible using more object-oriented abstractions, e.g. using more `case classes` (structs), 
refactoring the `skichallenge` object into a class with inner representations (to avoid passing the resort and its 
dimensions as input parameters), etc.

### Accept input parameters and handling

The `Main` object could be a adapted to accept reading any input file provided bu the user. The code then would also 
need error handling for file existence and to enforce proper file formatting (correct dimensions with proper data). 