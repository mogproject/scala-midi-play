#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._
import scala.util.Random

val random = new Random(12345)
val rcvr = MidiSystem.getReceiver()


def sendMessage(event: Int, channel: Int, data1: Int, data2: Int): Unit = {
  val msg = new ShortMessage
  msg.setMessage(event, channel, data1, data2)
  rcvr.send(msg, -1)
}


def noteOn(key: Int, duration: Long): Unit = {
  sendMessage(NOTE_ON, 0, key, 93)
  Thread.sleep(duration)
  sendMessage(NOTE_OFF, 0, key, 0)
}


def f(x: Int): Unit = {
  println(s"${x}: start")
  noteOn(48 + x, 300L * (random.nextInt(8) + 1))
  println(s"${x}: end")
}

// Change the program to string ensamble.
sendMessage(PROGRAM_CHANGE, 0, 49, 0)

println("### (21 to 30).foreach(f)")
(21 to 30).foreach(f)
Thread.sleep(2000L)

println("### (1 to 30).par.foreach(f)")
(1 to 30).par.foreach(f)
Thread.sleep(2000L)

