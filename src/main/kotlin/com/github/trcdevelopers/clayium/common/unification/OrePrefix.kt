package com.github.trcdevelopers.clayium.common.unification

import com.github.trcdevelopers.clayium.common.unification.material.Material
import com.github.trcdevelopers.clayium.common.unification.material.MaterialProperty
import com.github.trcdevelopers.clayium.common.util.CUtils

class OrePrefix(
    val camel: String,
    val snake: String = CUtils.toLowerSnake(camel),
    val generateCondition: (Material) -> Boolean,
) {


    fun doGenerateItem(material: Material): Boolean {
        return generateCondition(material)
    }

    companion object {
        val ingot = OrePrefix("ingot") { it.hasProperty<MaterialProperty.Ingot>() }
        val matter = OrePrefix("matter") { it.hasProperty<MaterialProperty.Matter>() }
        val dust = OrePrefix("dust") { it.hasProperty<MaterialProperty.Dust>() }
        val impureDust = OrePrefix("impureDust") { it.hasProperty<MaterialProperty.ImpureDust>() }
        val plate = OrePrefix("plate") { it.hasProperty<MaterialProperty.Plate>() }
        val largePlate = OrePrefix("largePlate") { it.hasProperty<MaterialProperty.Plate>() }

        val prefixes = setOf(ingot, matter, dust, impureDust, plate, largePlate)
    }
}