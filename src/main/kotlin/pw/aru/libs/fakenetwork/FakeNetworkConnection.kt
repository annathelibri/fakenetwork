package pw.aru.libs.fakenetwork

import java.io.Closeable
import java.io.DataOutputStream

/**
 * A [FakeNetwork] connection which lets you initiate multicasts and connections.
 */
interface FakeNetworkConnection : Closeable {
    /**
     * Creates a [DataOutputStream] which sends it's contents to every connection.
     */
    fun multicastDatagram() : DataOutputStream

    /**
     * Creates a [FakeSocket] and starts a connection to another device.
     */
    fun connect(address: String) : FakeSocket
}