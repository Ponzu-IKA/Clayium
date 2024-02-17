package com.github.trcdevelopers.clayium.common.items.metaitem

import com.github.trcdevelopers.clayium.common.Clayium
import com.github.trcdevelopers.clayium.common.items.metaitem.component.IItemColorHandler
import com.github.trcdevelopers.clayium.common.unification.OrePrefix
import com.github.trcdevelopers.clayium.common.unification.material.Material
import com.github.trcdevelopers.clayium.common.unification.material.MaterialProperty
import com.github.trcdevelopers.clayium.common.util.CUtils
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraftforge.client.model.ModelLoader

open class MetaPrefixItem private constructor(
    val name: String,
    val orePrefix: OrePrefix,
) : MetaItemClayium(name) {

    open fun registerSubItems() {
        for (material in Material.entries) {
            if (!orePrefix.doGenerateItem(material)) continue

            addItem(material.uniqueId.toShort(), material.materialName) {
                tier(material.tier)
                oreDict("${orePrefix.name}${CUtils.toUpperCamel(material.materialName)}")
                if (material.colors != null) {
                    addComponent(IItemColorHandler { _, i -> material.colors[i] })
                }
            }
        }
    }

    override fun registerModels() {
        for (item in metaValueItems.values) {
            val material = getMaterial(item.meta.toInt()) ?: continue
            if (material.colors == null) {
                ModelLoader.setCustomModelResourceLocation(this, item.meta.toInt(), ModelResourceLocation("${Clayium.MOD_ID}:${material.materialName}_${orePrefix.name}", "inventory"))
            } else {
                ModelLoader.setCustomModelResourceLocation(this, item.meta.toInt(), ModelResourceLocation("${Clayium.MOD_ID}:colored/${orePrefix.name}", "inventory"))
            }
        }
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        val material = getMaterial(stack) ?: return "invalid"
        return "${I18n.format("material.clayium.${material.materialName}")} ${I18n.format("ore.clayium.${orePrefix.name}")}"
    }

    private fun getMaterial(stack: ItemStack): Material? {
        return Material.fromId(stack.itemDamage)
    }

    private fun getMaterial(id: Int): Material? {
        return Material.fromId(id)
    }

    companion object {
        fun create(name: String, orePrefix: OrePrefix): MetaPrefixItem {
            return when (orePrefix) {
                OrePrefix.impureDust -> MetaPrefixItemImpureDust
                OrePrefix.matter -> object : MetaPrefixItem(name, OrePrefix.matter) {
                    override fun registerModels() {
                        for (item in metaValueItems.values) {
                            ModelLoader.setCustomModelResourceLocation(
                                this, item.meta.toInt(),
                                Material.fromId(item.meta.toInt())?.getProperty<MaterialProperty.Matter>()?.modelLocation ?: ModelLoader.MODEL_MISSING
                            )
                        }
                    }
                }
                else -> MetaPrefixItem(name, orePrefix)
            }
        }
    }

    private object MetaPrefixItemImpureDust : MetaPrefixItem("meta_impure_dust", OrePrefix.impureDust) {
       override fun registerSubItems() {
            for (material in Material.entries) {
                if (OrePrefix.impureDust.doGenerateItem(material)) {
                    val impureDust  = material.getProperty<MaterialProperty.ImpureDust>()!!
                    addItem(material.uniqueId.toShort(), material.materialName)
                        .tier(material.tier)
                        .addComponent(IItemColorHandler { _, i -> impureDust.getColor(i) })
                        .oreDict("impureDust${CUtils.toUpperCamel(material.materialName)}")
                }
            }
        }

        override fun registerModels() {
            for (item in metaValueItems.values) {
                ModelLoader.setCustomModelResourceLocation(
                    this, item.meta.toInt(),
                    ModelResourceLocation("${Clayium.MOD_ID}:colored/dust", "inventory")
                )
            }
        }
    }
}