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

- If you want to use non-default output device, see Appendix A.


### 2. Play music just using `NOTE_ON` and `Thread.sleep`

```
$ ./scripts/hello_midi.scala
```

### 3. Sonificate your concurrency

Not visualize but sonificate your Scala code's concurrency.

```
$ ./scripts/concurrency_sonification.scala
```

- Since the `Acoustic Piano` decays too fast, this script uses `String Ensemble 1` program.

### 4. Read events from MIDI device

```
$ ./scripts/play_back.scala
```

### 5. Write Scala code by MIDI device

todo

## References

- [scala-midi - Scala API over javax.sound.midi - Google Project Hosting](https://code.google.com/p/scala-midi/) 
- [Transmitting and Receiving MIDI Messages (The Java™ Tutorials > Sound)](http://docs.oracle.com/javase/tutorial/sound/MIDI-messages.html)
- [javax.sound.midi (Java Platform SE 7 )](http://docs.oracle.com/javase/7/docs/api/javax/sound/midi/package-summary.html)

## Appendix

### A. Specify the output device

If you want to use non-default output device, try this code!

```
import javax.sound.midi._
import javax.sound.midi.ShortMessage._

val devices = MidiSystem.getMidiDeviceInfo().map(MidiSystem.getMidiDevice)

// Print and choose one device you want to use.
devices.zipWithIndex.foreach { case (d, i) => println(s"${i} ${d.getDeviceInfo} (${d.getClass.getSimpleName})") }

val dev = devices(0)

dev.open()
val rcvr = dev.getReceiver()

val msg = new ShortMessage
msg.setMessage(NOTE_ON, 0, 60, 93)

rcvr.send(msg, -1)

dev.close()
```

