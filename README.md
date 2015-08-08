scala-midi-play
====

Play with MIDI using Java Sound API


## Introduction

### 1. Play the first tone

In the Scala REPL... *No additional library is needed*

```
import javax.sound.midi._
import javax.sound.midi.ShortMessage._

val rcvr = MidiSystem.getReceiver()

val msg = new ShortMessage
msg.setMessage(NOTE_ON, 0, 60, 93)

rcvr.send(msg, -1)
```

### 2. Play music just using `NOTE_ON` and `Thread.sleep`

```
$ ./scripts/hello_midi.scala
```

### 3. ~~Visualize~~ Sonificate your concurrency

```
$ ./scripts/concurrency_sonification.scala
```

Since the `Acoustic Piano` decays too fast, this script uses `String Ensemble 1` program.

### 4. Read events from MIDI device

todo

### 5. Write code by MIDI device

todo

## References

- [scala-midi - Scala API over javax.sound.midi - Google Project Hosting](https://code.google.com/p/scala-midi/) 
- [Transmitting and Receiving MIDI Messages (The Java™ Tutorials > Sound)](http://docs.oracle.com/javase/tutorial/sound/MIDI-messages.html)

