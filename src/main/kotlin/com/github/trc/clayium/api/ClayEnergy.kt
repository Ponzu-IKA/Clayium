package com.github.trc.clayium.api

import net.minecraft.network.PacketBuffer
import kotlin.math.abs
import kotlin.math.pow
import kotlin.text.format

fun PacketBuffer.writeClayEnergy(energy: ClayEnergy) {
    writeLong(energy.energy)
}

fun PacketBuffer.readClayEnergy(): ClayEnergy {
    return ClayEnergy(readLong())
}

@JvmInline
value class ClayEnergy(val energy: Long) : Comparable<ClayEnergy> {

    //todo: minimum digits?
    fun format(): String {
        return "${formatWithoutUnit()}CE"
    }

    fun formatWithoutUnit(): String {
        if (energy == 0L) return "0"
        val digits = abs(energy).toString().length
        val microCe = energy.toDouble() * 10.0
        val unitIndex = digits / 3
        val displayValue = String.format("%.3f", microCe / 10.0.pow(unitIndex * 3))
            .replace(matchesExcessZero, "")
            .replace(matchesExcessDecimalPoint, "")
        return "$displayValue${units[unitIndex]}"
    }

    override fun toString(): String {
        return "ClayEnergy(energy=$energy)"
    }

    operator fun plus(other: ClayEnergy) = ClayEnergy(energy + other.energy)
    operator fun minus(other: ClayEnergy) = ClayEnergy(energy - other.energy)
    operator fun times(value: Int) = ClayEnergy(energy * value)
    operator fun times(value: Long) = ClayEnergy(energy * value)
    operator fun times(value: Double) = ClayEnergy((energy * value).toLong())
    operator fun div(value: Int) = ClayEnergy(energy / value)
    operator fun div(value: Double) = ClayEnergy((energy.toDouble() / value).toLong())
    override operator fun compareTo(other: ClayEnergy) = energy.compareTo(other.energy)

    companion object {
        val ZERO = ClayEnergy(0)
        val MAX = ClayEnergy(Long.MAX_VALUE)

        val units = listOf("u", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y")
        private val matchesExcessZero = Regex("0+\$")
        private val matchesExcessDecimalPoint = Regex("\\.$")

        fun micro(energy: Long): ClayEnergy {
            require(energy % 10 == 0.toLong()) {
                "10μ CE is a minimum unit of Clay Energy, but the given value is not a multiple of 10μ CE: $energy"
            }
            return ClayEnergy(energy / 10)
        }

        fun milli(energy: Long): ClayEnergy {
            return ClayEnergy(energy * 100)
        }

        fun of(energy: Long): ClayEnergy {
            return ClayEnergy(energy * 1000_00)
        }
    }
}