package com.github.trcdevelopers.clayium.api.metatileentity

import com.github.trcdevelopers.clayium.api.capability.ClayiumTileCapabilities
import com.github.trcdevelopers.clayium.api.capability.impl.AbstractRecipeLogic
import com.github.trcdevelopers.clayium.api.capability.impl.ClayEnergyHolder
import com.github.trcdevelopers.clayium.api.capability.impl.ItemHandlerProxy
import com.github.trcdevelopers.clayium.api.capability.impl.NotifiableItemStackHandler
import com.github.trcdevelopers.clayium.api.util.ITier
import com.github.trcdevelopers.clayium.common.blocks.machine.MachineIoMode
import com.github.trcdevelopers.clayium.common.recipe.registry.RecipeRegistry
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class WorkableMetaTileEntity(
    metaTileEntityId: ResourceLocation,
    tier: ITier,
    validInputModes: List<MachineIoMode>,
    validOutputModes: List<MachineIoMode>,
    translationKey: String,
    val recipeRegistry: RecipeRegistry<*>,
) : MetaTileEntity(metaTileEntityId, tier, validInputModes, validOutputModes, translationKey) {

    val inputSize = recipeRegistry.maxInputs
    val outputSize = recipeRegistry.maxOutputs

    override val importItems = NotifiableItemStackHandler(this, inputSize, this, false)
    override val exportItems = NotifiableItemStackHandler(this, outputSize, this, true)
    override val itemInventory = ItemHandlerProxy(importItems, exportItems)
    override val autoIoHandler = AutoIoHandler.Combined(this)

    val clayEnergyHolder = ClayEnergyHolder(this)
    abstract val workable: AbstractRecipeLogic

    @SideOnly(Side.CLIENT)
    override fun registerItemModel(item: Item, meta: Int) {
        ModelLoader.setCustomModelResourceLocation(item, meta, ModelResourceLocation("${recipeRegistry.category.modid}:${recipeRegistry.category.categoryName}", "tier=${tier.numeric}"))
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (capability == ClayiumTileCapabilities.CAPABILITY_CLAY_ENERGY_HOLDER) return ClayiumTileCapabilities.CAPABILITY_CLAY_ENERGY_HOLDER.cast(clayEnergyHolder)
        return super.getCapability(capability, facing)
    }

    override fun clearMachineInventory(itemBuffer: MutableList<ItemStack>) {
        super.clearMachineInventory(itemBuffer)
        clearInventory(itemBuffer, clayEnergyHolder.energizedClayItemHandler)
    }
}