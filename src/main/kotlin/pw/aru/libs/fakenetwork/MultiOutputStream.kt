package pw.aru.libs.fakenetwork

import java.io.OutputStream
import java.io.PipedOutputStream

/**
 * An [OutputStream] that writes into multiple [OutputStream]s.
 *
 * It should be fairly safe as its run it's operations catching and discarding the results.
 * [OutputStream]s which write contents will cause undefined behaviour.
 */
class MultiOutputStream : OutputStream() {
    private val streams = ArrayList<OutputStream>()

    /**
     * Adds an [OutputStream] to the list of this stream.
     */
    fun add(outputStream: OutputStream) = apply {
        streams += outputStream
    }

    /**
     * Creates a [PipedOutputStream] and adds it to the list of this stream, then returns the newly created [PipedOutputStream].
     */
    fun newPiped(): PipedOutputStream {
        return PipedOutputStream().also(streams::plusAssign)
    }

    override fun write(b: Int) {
        streams.forEach { it.runCatching { write(b) } }
    }

    override fun write(b: ByteArray) {
        streams.forEach { it.runCatching { write(b.copyOf()) } }
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        streams.forEach { it.runCatching { write(b.copyOf(), off, len) } }
    }

    override fun flush() {
        streams.forEach { it.runCatching { flush() } }
    }

    override fun close() {
        streams.forEach { it.runCatching { close() } }
    }
}