package y2017

import kotlin.math.absoluteValue

fun main(args: Array<String>) {
	part1().println()
	part2().println()
}

private fun getInput() = readInput(20)
		.mapIndexedNotNull(::toParticle)
		.toList()

private data class Particle(val index: Int, val position: Vector, val velocity: Vector, val acceleration: Vector)
private data class Vector(val x: Int, val y: Int, val z: Int)

private val Vector.distance get() = x.absoluteValue + y.absoluteValue + z.absoluteValue
private operator fun Vector.plus(other: Vector) = Vector(x + other.x, y + other.y, z + other.z)

private fun toParticle(index: Int, line: String) = Regex("p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>")
		.matchEntire(line)
		?.let {
			it.groupValues.drop(1).map(String::toInt).let { Particle(index, Vector(it[0], it[1], it[2]), Vector(it[3], it[4], it[5]), Vector(it[6], it[7], it[8])) }
		}

private fun part1(particles: List<Particle> = getInput()) =
		particles.fold(Int.MAX_VALUE to listOf<Particle>()) { (smallestAcceleration, particles), particle ->
			when {
				particle.acceleration.distance < smallestAcceleration -> particle.acceleration.distance to listOf(particle)
				particle.acceleration.distance == smallestAcceleration -> smallestAcceleration to (particles + particle)
				else -> smallestAcceleration to particles
			}
		}.second.first().index

private fun part2(particles: List<Particle> = getInput()) =
		generateSequence((0 to particles.size) to particles) { foo ->
			val (iteration, number) = foo.first
			val particles = foo.second
			(particles - (particles
					.groupBy { it.position }
					.filter { it.value.size > 1 }
					.flatMap { it.value }))
					.map { particle -> Particle(particle.index, particle.position + particle.velocity + particle.acceleration, particle.velocity + particle.acceleration, particle.acceleration) }
					.let { remainingParticles ->
						when {
							remainingParticles.size < number -> (0 to remainingParticles.size) to remainingParticles
							remainingParticles.size == number -> if (iteration > 100) null else (iteration + 1 to number) to remainingParticles
							else -> throw RuntimeException()
						}
					}
		}.last().second.size
