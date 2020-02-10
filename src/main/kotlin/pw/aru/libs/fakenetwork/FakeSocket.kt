package pw.aru.libs.fakenetwork

import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * A fake socket connection within a [FakeNetwork] between [FakeNetworkDevice]s.
 */
interface FakeSocket : Closeable {
    /**
     * The address of the connected device on the other end.
     */
    val address: String

    /**
     * The input stream to the other end.
     */
    val input: DataInputStream

    /**
     * The output stream to the other end.
     */
    val output: DataOutputStream
}

