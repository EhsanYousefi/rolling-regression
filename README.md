# Fyber - Challenge 1 - TimeSeries
Performing rolling regression allows us to check for changes in the regression coefficients over time. My solution to the task is quite simple and powerful, here is the gist of it:

    -> Start reading from source iteratively

    -> Parse measurement in each iteration(Exit if measurement didn't parse successfully)

    -> As parsing measurement continues partition those that fall withing a same window length and ...

    -> After generating each partition(window), compute it's local information in parallel and push the potential result into consumer's queue.

    -> When consumer's queue has reached to its bound, emit them consecutively to given sinks(i.e StandardOutput)`

Code is quite self-explanatory but not optimized as I wanted, for example parsing/partitioning section could be implemented in parallel manner.
 
## Instructions
Application's entry takes two arguments, first one is file path and another is consumer's queue size.
