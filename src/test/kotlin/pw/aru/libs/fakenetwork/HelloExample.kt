package pw.aru.libs.fakenetwork

import java.io.DataInputStream

class HelloDevice(override val address: String) : FakeNetworkDevice {
    override fun onMulticast(address: String, datagram: DataInputStream) {
        datagram.use {
            println("[M][$address] ${it.readUTF()}")
        }
    }

    override fun onConnection(connection: FakeSocket) {
        println("[C][$address][from ${connection.address}] ${connection.input.readUTF()}")
        connection.output.writeUTF("Hi from $address")
    }
}

fun main() {
    val network = FakeNetwork()

    val conn1 = network.connectDevice(HelloDevice("10.0.0.1"))
    val conn2 = network.connectDevice(HelloDevice("10.0.0.2"))
    val conn3 = network.connectDevice(HelloDevice("10.0.0.3"))

    conn1.multicastDatagram().use {
        it.writeUTF("Hello everyone this is 10.0.0.1")
    }
    conn2.connect("10.0.0.3").let {
        it.output.writeUTF("Hi, it's 10.0.0.2")
        println("[C][10.0.0.2][from ${it.address}] ${it.input.readUTF()}")
    }
}

