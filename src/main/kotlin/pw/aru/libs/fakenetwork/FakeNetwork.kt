package pw.aru.libs.fakenetwork

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread

/**
 * A fake network built upon [PipedInputStreams][PipedInputStream] and [PipedOutputStreams][PipedOutputStream].
 *
 * You can extend it to customize its thread-spawning behaviour.
 */
open class FakeNetwork {
    private val devices = ConcurrentHashMap<String, FakeNetworkDevice>()

    /**
     * Registers the device listeners, returns a [FakeNetworkConnection] instance.
     */
    fun connectDevice(device: FakeNetworkDevice): FakeNetworkConnection {
        check(!devices.containsKey(device.address)) { "Device ${device.address} already exists on the Network" }
        devices[device.address] = device
        return FkNetConn(device.address)
    }

    private inner class FkNetConn(private val address: String) :
        FakeNetworkConnection {
        override fun multicastDatagram(): DataOutputStream {
            val out = MultiOutputStream()
            for ((address, device) in devices) {
                if (address == this.address) continue
                val stream = DataInputStream(PipedInputStream(out.newPiped()))
                runOnOtherThread(this.address, address) { device.onMulticast(address, stream) }
            }
            return DataOutputStream(out)
        }

        override fun connect(address: String): FakeSocket {
            val clientAddr = this.address
            val serverAddr = address

            val serverIn = PipedInputStream()
            val clientIn = PipedInputStream()
            val serverOut = PipedOutputStream(clientIn)
            val clientOut = PipedOutputStream(serverIn)

            val clientConn = FkSock(clientAddr, DataInputStream(clientIn), DataOutputStream(clientOut))
            val serverConn = FkSock(serverAddr, DataInputStream(serverIn), DataOutputStream(serverOut))
            runOnOtherThread(clientAddr, serverAddr) { devices[serverAddr]!!.onConnection(clientConn) }
            return serverConn
        }

        override fun close() {
            devices.remove(address)
        }
    }

    private class FkSock(
        override val address: String,
        override val input: DataInputStream,
        override val output: DataOutputStream
    ) : FakeSocket {
        override fun close() {
            input.runCatching { close() }
            output.runCatching { close() }
        }
    }

    protected open fun runOnOtherThread(vararg address: String, block: () -> Unit) {
        thread(name = "${toString()}[${address.joinToString()}]-Thread", block = block)
    }
}
