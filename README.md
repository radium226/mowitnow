# MowItNow [![Build Status](https://travis-ci.org/radium226/mowitnow.svg?branch=master)](https://travis-ci.org/radium226/mowitnow) [![Dependency Status](https://www.versioneye.com/user/projects/57189df5fcd19a004544176d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/57189df5fcd19a004544176d) [![Coverage Status](https://coveralls.io/repos/github/radium226/mowitnow/badge.svg?branch=master)](https://coveralls.io/github/radium226/mowitnow?branch=master)

## Usage

### Command Line
You first have to clone the sources. Then, simply do an `sbt assembly` to produce the fat-JAR which can be used directly from command line. Then you can do:
```
java <<EOCAT -jar "./target/scala-2.11/mowitnow-assembly-1.0.jar"
5 5
1 2 N
GAGAGAGAA
3 3 E
AADAADADDA
EOCAT
```
You should see:
```
1 3 N
5 1 E
```

The exit status is `0` if everything goes fine and `1` if not (parsing error, inconsistent state). 

You can enable the debug mode with the `-d` flag, use a file as output using the `-o` option and you can provide a single argument to use a file as input: `java -jar "mowitnow-assembly-1.0.jar" -d -o "output.txt" "input.txt"`

### API
You can use the mower programatically:
```scala
import com.github.radium226.mowitnow._
import com.github.radium226.mowitnow.Action._
import com.github.radium226.mowitnow.Orientation._
import com.github.radium226.mowitnow.Direction._

val actions = Seq(TurnLeft(), MoveForward(), TurnRight(), MoveForward()) # You define the actions the mower should follow
val mower = new Mower(actions) # You instanciate the mower
val initialState = State(Position(0, 0), North()) # You should define an initial state
val size = Size(10, 10) # And the size of the lawn
mower.mow(initialState)(size) match { # And you mow! 
 case Success(finalState) => println(finalState) # # If it's okay, you retreive an instance of State
 case Failure(throwable) => println(throwable.getMessage) # Otherwise, you can retreive the cause of the failure 
}
```
More details in the [Scaladoc](https://radium226.github.io/mowitnow).

## To do
- [ ] Put the library in the Maven Repository
- [ ] Define a release process
- [ ] Replace the badges by the HD ones
