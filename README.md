# Lego Boost Java Library
This library is used to control the Lego Boost from Java.
Unfortunatly, the Bluetooth support in Java isn't that great.
This library uses the GATTTool command line tool to invoke the bluetooth commands.
As a result, this library does not work unless you have this tool installed.

![Alt Text](https://github.com/TomCools/BoostThatJava/blob/master/static/vernie.gif)

## Requirements
- Java 8 or higher
- A Lego Boost
- Any OS with GATTtool command line

I personally used a Raspberry Pi 3, and the Hypriot OS: https://blog.hypriot.com/downloads/

## How does this example work?
The most important class is **GatttoolCommandWrapper**. This class enables you to execute bluetooth commands using the Gatttool installed on your OS.

The tricky part however are encoding the commands into the required Hexadecimal commands. The **CommandEncoder** class does this transformation.
I found a lot of the details on how to do this here: https://github.com/JorgePe/BOOSTreveng/

### What Works?
- Turn Motors by Angle
- Turn Motors for a Certain Time

I have focussed on the Vernie Bot you can build using Lego Boost. 
- Motor A and B: "Feet" of Vernie, used to drive around with Vernie.
- Motor C: "Head". Makes Vernie moves his head. This also allows him to fire his little rocket.

### Running the example
Example built using Vert.x (http://vertx.io/).

Clone this git repository onto your RPI3 or equivalent and run:
**mvnw compile exec:java** while keeping the button pressed on the Lego Boost Hub.
Once connected succesfully, the led on the Boost Hub will change to a Blue Color.

This example exposes a HTTP API on port 8080 _(see HttpVerticle.class)_.
You can do:

- POST /vernie/{COMMAND}: Makes vernie run a precoded command. _(see VernieVerticle.class)_
- GET /config: Gets the configured details
- PUT /config: Changes the configuration
- /*: Any other endpoint returns "I'm alive" to signify the software is running

## Main Learning Resources
https://github.com/JorgePe/BOOSTreveng  
https://github.com/pcborenstein/bluezDoc/wiki/hcitool-and-gatttool-example  
