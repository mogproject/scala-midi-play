#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._

val rcvr = MidiSystem.getReceiver()

def noteOn(key: Int, duration: Long) = {
  val msg = new ShortMessage

  msg.setMessage(NOTE_ON, 0, key, 93)
  rcvr.send(msg, -1)

  Thread.sleep(duration)

  msg.setMessage(NOTE_OFF, 0, key, 0)
  rcvr.send(msg, -1)
}

val bpm = 66
val sq = 60L * 1000 / bpm / 4 // semiquaver duration in milli second

println("4")
Thread.sleep(4 * sq)
println("3")
Thread.sleep(4 * sq)
println("2")
Thread.sleep(4 * sq)
println("1")
Thread.sleep(4 * sq)

println("")
Thread.sleep(2 * sq)
print("冷")
noteOn(67, sq)
print("や")
noteOn(67, sq)
print("し")
noteOn(67, sq)
print("中")
noteOn(67, sq)
noteOn(66, sq)
print("華")
noteOn(64, 4 * sq)
print(" ")
Thread.sleep(1 * sq)
print("は")
noteOn(61, 4 * sq / 3)
print("じ")
noteOn(62, 4 * sq / 3)
print("め")
noteOn(64, 4 * sq / 3)
print("ま")
noteOn(64, sq)
print("し")
noteOn(62, sq)
print("た")
noteOn(62, 14 * sq)

println("\n\n(c) AMEMIYA")

