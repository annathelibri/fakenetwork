package pw.aru.libs.fakenetwork

import java.io.DataInputStream

/**
 * Class you should implement (or use the invoke-constructor) to interface with [FakeNetwork].
 */
interface FakeNetworkDevice {
    /**
     * The device's address.
     */
    val address: String

    /**
     * Handler for multicast datagrams.
     */
    fun onMulticast(address: String, datagram: DataInputStream) {}

    /**
     * Handler for [FakeSocket] connections.
     */
    fun onConnection(connection: FakeSocket) {}

    companion object {
        val IGNORE_MULTICASTS: OnMulticast = { _, i -> i.close() }
        val IGNORE_CONNECTIONS: OnConnection = { it.close() }

        /**
         * Creates a [FakeNetworkDevice] using the parameters given.
         */
        operator fun invoke(address: String, onMulticast: OnMulticast = IGNORE_MULTICASTS, onConnection: OnConnection = IGNORE_CONNECTIONS): FakeNetworkDevice {
            return object : FakeNetworkDevice {
                override val address = address
                override fun onMulticast(address: String, datagram: DataInputStream) {
                    onMulticast.invoke(address, datagram)
                }

                override fun onConnection(connection: FakeSocket) {
                    onConnection.invoke(connection)
                }
            }
        }
    }
}