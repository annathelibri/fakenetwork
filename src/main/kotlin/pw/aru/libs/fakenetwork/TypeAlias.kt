package pw.aru.libs.fakenetwork

import java.io.DataInputStream
import kotlin.ParameterName as PName

typealias OnMulticast = (@PName("address") String, @PName("datagram") DataInputStream) -> Unit
typealias OnConnection = (@PName("connection") FakeSocket) -> Unit