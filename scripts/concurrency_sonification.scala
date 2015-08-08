#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._
import scala.util.Random

val random = new Random(12345)
val rcvr = MidiSystem.getReceiver()

def changeProgram(program: Int) = {
  val msg = new ShortMessage
  msg.setMessage(PROGRAM_CHANGE, 0, program, 0)
  rcvr.send(msg, -1)
}

def noteOn(key: Int, duration: Long) = {
  val msg = new ShortMessage

  msg.setMessage(NOTE_ON, 0, key, 93)
  rcvr.send(msg, -1)

  Thread.sleep(duration)

  msg.setMessage(NOTE_OFF, 0, key, 0)
  rcvr.send(msg, -1)
}


def f(x: Int): Unit = {
  println(s"${x}: start")
  noteOn(48 + x, 500L + random.nextInt(2000))
  println(s"${x}: end")
}

// Change the program to string ensamble.
changeProgram(49)

(1 to 30).par.foreach(f)

