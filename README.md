# prog-benchmark-javajs
this project for benchmark about Javascript engine written with Java.

## rhino vs nashorn
Now, I test a couple of engine, rhino and nashorn.

## Result
Rhino is better than Nashorn

- rhino:nashorn = 60 : 648 (ms). // Define 1000 function test
- rhino:nashorn = 2 : 816 (ms).  // Use function 1000 time test
- rhino:nashorn = 1 : 96 (ms).   // calcurate 1000 time test

