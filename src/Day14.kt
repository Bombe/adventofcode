fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(14).first()

private fun part1(key: String = getInput()) =
		(0 until 128)
				.map { "$key-$it" }
				.map { it.toByteArray().toList() }
				.map { knotHash(it) }
				.map { it.map { Integer.bitCount(it.toInt() and 0xff) }.sum() }
				.sum()

private fun part2(key: String = getInput()) =
		(0 until 128)
				.map { "$key-$it" }
				.map { it.toByteArray().toList() }
				.map { knotHash(it) }
				.toGrid()
				.let { grid ->
					(0 until grid.height).forEach { y ->
						(0 until grid.width).forEach { x ->
							grid.markRegion(x, y)
						}
					}
					grid.regionCount
				}

private fun List<List<Byte>>.toGrid() = Grid().apply {
	this@toGrid
			.map { it.joinToString("") { it.toBits() } }
			.forEachIndexed { rowIndex, row ->
				row.forEachIndexed { columnIndex, char ->
					this[columnIndex, rowIndex] = if (char == '1') 0 else -1
				}
			}
}

private fun Byte.toBits() = "0000000${Integer.toBinaryString(this.toInt() and 0xff)}".takeLast(8)

class Grid(val width: Int = 128, val height: Int = 128) {

	private val spaces = IntArray(width * height)
	private val nextRegion get() = spaces.max()!! + 1
	val regionCount get() = spaces.max()!!

	operator fun get(x: Int, y: Int) = if ((x in 0 until width) && (y in 0 until height)) spaces[y * width + x] else null
	operator fun set(x: Int, y: Int, value: Int) {
		spaces[y * width + x] = value
	}

	fun markRegion(x: Int, y: Int, region: Int = nextRegion) {
		if ((x in 0 until width) and (y in 0 until height)) {
			if (this[x, y] == 0) {
				this[x, y] = region
				markRegion(x - 1, y, region)
				markRegion(x + 1, y, region)
				markRegion(x, y - 1, region)
				markRegion(x, y + 1, region)
			}
		}
	}

}
