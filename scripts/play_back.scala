#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._


def getOutDevice(): MidiDevice = {
  // Use Gervill synthesizer
  val info = MidiSystem.getMidiDeviceInfo().filter(_.getName == "Gervill").headOption
  val device = info.map(MidiSystem.getMidiDevice).getOrElse {
    println("[ERROR] Could not find Gervill synthesizer.")
    sys.exit(1)
  }

  device.open()
  Thread.sleep(2000L)  // wait for device initialize
  device
}


def getInDevice(): MidiDevice = {
  val info = MidiSystem.getMidiDeviceInfo()
  val device = info.map(MidiSystem.getMidiDevice)
    .filter(_.getClass.getSimpleName == "MidiInDevice")
    .headOption
    .getOrElse {
      println("[ERROR] Could not find MIDI input device.")
      sys.exit(1)
    }

  device.open()
  device
}


class MidiInputReceiver(rcvr: Receiver) extends Receiver {
  override def send(msg: MidiMessage, timeStamp: Long): Unit = {
    val status = Map(
      NOTE_ON -> "NOTE_ON ",
      NOTE_OFF -> "NOTE_OFF",
      CONTROL_CHANGE -> "C.CHANGE"
    ).getOrElse(msg.getStatus, msg.getStatus)
    println(s"${status}: ${msg.getMessage.toSeq}")
    rcvr.send(msg, -1)
  }

  override def close(): Unit = {}
}


// Get output receiver
val rcvr = getOutDevice().getReceiver()

// Set transmitter
val trans = getInDevice().getTransmitter()
trans.setReceiver(new MidiInputReceiver(rcvr))

println("Hello!")
println("Press Ctlr-C to quit.")

