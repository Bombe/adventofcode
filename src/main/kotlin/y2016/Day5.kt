package y2016

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
	getInput(5).first().let {
		println(first(it))
		println(second(it))
	}
}

private fun first(input: String) = generateSequence(0, { it + 1 })
		.map { hash(input, it) }
		.filter { it.startsWith("00000") }
		.map { it.substring(5..5) }
		.take(8)
		.joinToString("")

private fun second(input: String) = generateSequence(0, { it + 1 })
		.map { hash(input, it) }
		.filter { it.startsWith("00000") }
		.map { it.substring(5..5).parseHex() to it.substring(6..6) }
		.filter { it.first < 8 }
		.distinctBy { it.first }
		.take(8)
		.sortedBy { it.first }
		.map { it.second }
		.joinToString("")

private val md5 = MessageDigest.getInstance("MD5")

private fun hash(input: String, index: Int) =
		DatatypeConverter.printHexBinary(md5.digest("$input$index".toByteArray()))

private fun String.parseHex() = Integer.parseInt(this, 16)
