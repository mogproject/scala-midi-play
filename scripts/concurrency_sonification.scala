#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._
import scala.util.Random

val random = new Random(12345)

// Use Gervill synthesizer
val info = MidiSystem.getMidiDeviceInfo().filter(_.getName == "Gervill").headOption
val device = info.map(MidiSystem.getMidiDevice).getOrElse {
   println("[ERROR] Could not find Gervill synthesizer.")
   sys.exit(1)
}

// Setup output device
device.open()
Thread.sleep(2000L)  // wait for device initialize
val rcvr = device.getReceiver()


def sendMessage(event: Int, channel: Int, data1: Int, data2: Int): Unit = {
  val msg = new ShortMessage
  msg.setMessage(event, channel, data1, data2)
  rcvr.send(msg, -1)
}


def noteOn(key: Int, gateTime: Long): Unit = {
  sendMessage(NOTE_ON, 0, key, 70)
  Thread.sleep(gateTime)
  sendMessage(NOTE_OFF, 0, key, 0)
}


def f(x: Int): Unit = {
  println(s"${x}: start")
  val scale = List(0, 2, 4, 5, 7, 9, 11)
  val key = 36 + x / scale.length * 12 + scale(x % scale.length)
  noteOn(key, 240L * (random.nextInt(8) + 1))
  println(s"${x}: end")
}

// Change the program to string ensamble.
sendMessage(PROGRAM_CHANGE, 0, 49, 0)

println("### (7 to 14).foreach(f)")
(7 to 14).foreach(f)
Thread.sleep(2000L)

println("### (0 to 28).par.foreach(f)")
(0 to 28).par.foreach(f)
Thread.sleep(2000L)
device.close()

