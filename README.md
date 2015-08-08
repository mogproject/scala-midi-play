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

### 2. Play the music using only Java Sound API and `Thread.sleep`

```
$ ./scripts/hello_midi.scala
```

## References

- [scala-midi - Scala API over javax.sound.midi - Google Project Hosting](https://code.google.com/p/scala-midi/) 
- [Transmitting and Receiving MIDI Messages (The Java™ Tutorials > Sound)](http://docs.oracle.com/javase/tutorial/sound/MIDI-messages.html)

