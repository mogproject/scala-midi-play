#!/usr/bin/env scala

import javax.sound.midi._
import javax.sound.midi.ShortMessage._
import java.awt.Robot
import java.awt.event.KeyEvent._
import scala.util.Try

val robot = new Robot

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
    Try {
      (msg.getStatus, msg.getMessage()(1)) match {
        // This mapping is for US keyboard.
        case (NOTE_ON, 48) => pressKey(VK_P, VK_R, VK_I, VK_N, VK_T, VK_L, VK_N); pressKeyShift(VK_9)  // println(
        case (NOTE_OFF, 48) => pressKeyShift(VK_0); pressKey(VK_ENTER)  // )\n 
        case (NOTE_ON, 91) => pressKey(VK_S, VK_C, VK_A, VK_L, VK_A, VK_ENTER)  // scala\n
        case (NOTE_ON, 67) => pressKeyShift(VK_QUOTE)  // "
        case (NOTE_ON, 74) => pressKey(VK_D)  // d
        case (NOTE_ON, 76) => pressKey(VK_H)  // h
        case (NOTE_ON, 77) => pressKey(VK_E)  // e
        case (NOTE_ON, 79) => pressKey(VK_L)  // l
        case (NOTE_ON, 81) => pressKey(VK_R)  // r
        case (NOTE_ON, 83) => pressKey(VK_SPACE)  // ' '
        case (NOTE_ON, 84) => pressKey(VK_O)  // o
        case (NOTE_ON, 86) => pressKey(VK_W)  // w
        case _ =>
      }
    }.recover {
      case e: Throwable => print(e)
    }

    rcvr.send(msg, -1)
  }

  override def close(): Unit = {}

  private[this] def pressKey(keys: Int*): Unit = {
    keys.foreach { key =>
      robot.keyPress(key)
      robot.keyRelease(key)
    }
  }

  private[this] def pressKeyShift(keys: Int*): Unit = {
    robot.keyPress(VK_SHIFT)
    keys.foreach { key =>
      robot.keyPress(key)
      robot.keyRelease(key)
    }
    robot.keyRelease(VK_SHIFT)
  }
}

// Get output receiver
val rcvr = getOutDevice().getReceiver()

// Set transmitter
val trans = getInDevice().getTransmitter()
trans.setReceiver(new MidiInputReceiver(rcvr))

println("Hello!")
println("Press Ctlr-C to quit.")

