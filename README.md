# Lego Boost Java Library
This library is used to control the Lego Boost from Java.
Unfortunatly, the Bluetooth support in Java isn't that great.
This library uses the GATTTool command line tool to invoke the bluetooth commands.
As a result, this library does not work unless you have this tool installed.

## Requirements
- Java
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

![Alt Text](https://media.giphy.com/media/vFKqnCdLPNOKc/giphy.gif)

### Running the example
Clone this git repository onto your RPI3 or equivalent and run:
**mvnw compile exec:java**