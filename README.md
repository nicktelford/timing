Timing
=====

*Simple timing primitives and utilities*

Timing provides a set of simple utilities for timing measurements.

Initially, this includes a set of `Clock`s that provide monotonic measurements 
of the current time, with varying accuracy and performance characteristics.

NOTE: The license for this project is the Apache Software License version 2.0.
LICENSE file to be added shortly.

Requirements
------------

* Java 1.6

Dependencies
------------

To use `timing`, simply add it as a dependency:

```xml
<dependencies>
  <dependency>
    <groupId>net.nicktelford</groupId>
    <artifactId>timing</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

Or whatever you need to do to make `ivy`/`sbt`/`gradle`/etc. happy.

Usage
-----
See the JavaDoc in `net.nicktelford.timing.clocks.Clock` for usage of the 
Clocks.

