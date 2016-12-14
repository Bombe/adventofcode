package y2016

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
	println(first())
	println(second())
}

private fun first(): Int {
	val cache = mutableMapOf<Int, String>()
	var index = 0
	var found = 0
	while (found < 64) {
		val triplet = cache.getOrPut(index, { (salt + index).md5() }).triplet()
		if (triplet != null) {
			var tempIndex = 1
			while (tempIndex <= 1000) {
				if (cache.getOrPut(index + tempIndex, { (salt + (index + tempIndex)).md5() }).quintuplet() == triplet) {
					found++
					break
				}
				tempIndex++
			}
		}
		index++
	}
	return index - 1
}

private fun second(): Int {
	val cache = mutableMapOf<Int, String>()
	var index = 0
	var found = 0
	while (found < 64) {
		val triplet = cache.getOrPut(index, { (salt + index).stretchedMd5() }).triplet()
		if (triplet != null) {
			var tempIndex = 1
			while (tempIndex <= 1000) {
				if (cache.getOrPut(index + tempIndex, { (salt + (index + tempIndex)).stretchedMd5() }).quintuplet() == triplet) {
					found++
					break
				}
				tempIndex++
			}
		}
		index++
	}
	return index - 1
}

private fun <K, V> MutableMap<K, V>.getOrPut(index: K, defaultValue: (K) -> V) = if (index in this) this[index]!! else defaultValue(index).apply { this@getOrPut[index] = this }

private val md5 = MessageDigest.getInstance("MD5")!!

private fun String.md5() = md5.digest(this.toByteArray()).toHex()
private fun String.stretchedMd5() = (0..2016).fold(this, { hash, index -> md5.digest(hash.toByteArray()).toHex() })
private fun ByteArray.toHex() = DatatypeConverter.printHexBinary(this).toLowerCase()
private val tripletRegex = "(.)\\1\\1".toRegex()
private fun String.triplet() = tripletRegex.find(this)?.groupValues?.get(1)
private val quintupletRegex = "(.)\\1\\1\\1\\1".toRegex()
private fun String.quintuplet() = quintupletRegex.find(this)?.groupValues?.get(1)

private val salt = "ahsbgdzn"
