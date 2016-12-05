package y2016

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
	println(first2(getInput().first()))
	println(second())
}

private fun first(): String {
	val input = getInput().first()
	var index = 0
	var hashCode = ""
	while (hashCode.length < 8) {
		hash(input, index++).let {
			if (it.startsWith("00000")) {
				hashCode += it.substring(5..5)
			}
		}
	}
	return hashCode
}

private fun second() : String{
	val input = getInput().first()
	var index = 0
	var hashCode = "________"
	while ("_" in hashCode) {
		hash(input, index++).let {
			if (it.startsWith("00000")) {
				val position = it.substring(5..5).parseHex()
				if ((position < 8) && (hashCode[position] == '_')) {
					hashCode = hashCode.substring(0..(position - 1)) + it.substring(6..6) + hashCode.substring(position + 1)
				}
			}
		}
	}
	return hashCode
}

private val md5 = MessageDigest.getInstance("MD5")

private fun hash(input: String, index: Int) = md5.let {
	val hash = it.digest("$input$index".toByteArray())
	it.reset()
	DatatypeConverter.printHexBinary(hash)
}

private fun String.parseHex() = Integer.parseInt(this, 16)

private fun getInput(day: Int = 5) = AllDays().javaClass.getResourceAsStream("day$day.txt")
		.reader()
		.readLines()
