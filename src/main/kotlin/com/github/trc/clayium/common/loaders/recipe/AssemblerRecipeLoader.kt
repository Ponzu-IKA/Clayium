package com.github.trc.clayium.common.loaders.recipe

import com.github.trc.clayium.api.ClayEnergy
import com.github.trc.clayium.api.unification.material.CMarkerMaterials
import com.github.trc.clayium.api.unification.material.CMaterials
import com.github.trc.clayium.api.unification.ore.OrePrefix
import com.github.trc.clayium.common.blocks.ClayiumBlocks
import com.github.trc.clayium.common.blocks.marker.ClayMarkerType
import com.github.trc.clayium.common.items.ClayiumItems
import com.github.trc.clayium.common.items.metaitem.MetaItemClayParts
import com.github.trc.clayium.common.recipe.registry.CRecipes
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

object AssemblerRecipeLoader {
    fun registerRecipes() {
        val registry = CRecipes.ASSEMBLER

        //region Tools
        registry.builder()
            .input(ClayiumItems.CLAY_ROLLING_PIN)
            .input(ClayiumItems.CLAY_SLICER)
            .output(ClayiumItems.CLAY_IO_CONFIGURATOR)
            .tier(6).CEtFactor(1.0).duration(20)
            .buildAndRegister()
        registry.builder()
            .input(ClayiumItems.CLAY_SPATULA)
            .input(ClayiumItems.CLAY_WRENCH)
            .output(ClayiumItems.CLAY_PIPING_TOOL)
            .tier(6).CEtFactor(1.0).duration(20)
            .buildAndRegister()
        registry.builder()
            .input(OrePrefix.plate, CMaterials.az91d, 3)
            .input(MetaItemClayParts.SynchronousParts, 2)
            .output(ClayiumItems.SYNCHRONIZER)
            .tier(6).duration(20)
            .buildAndRegister()
        //endregion

        registry.builder()
            .input(MetaItemClayParts.AdvancedCircuit)
            .input(OrePrefix.plate, CMaterials.industrialClay)
            .output(ClayiumItems.simpleItemFilter)
            .tier(4).CEt(ClayEnergy.micro(80)).duration(20)
            .buildAndRegister()

        registry.builder()
            .input(OrePrefix.dust, CMaterials.quartz, 16)
            .output(ClayiumBlocks.QUARTZ_CRUCIBLE)
            .CEt(ClayEnergy.milli(10)).duration(20)
            .buildAndRegister()

        //region ClayParts
        for (m in listOf(CMaterials.clay, CMaterials.denseClay)) {
            registry.builder()
                .input(OrePrefix.stick, m, 5)
                .output(OrePrefix.gear, m)
                .tier(3).duration(20)
                .buildAndRegister()
            registry.builder()
                .input(OrePrefix.shortStick, m, 9)
                .output(OrePrefix.gear, m)
                .tier(3).duration(20)
                .buildAndRegister()
            registry.builder()
                .input(OrePrefix.largePlate, m)
                .input(Items.CLAY_BALL, 8)
                .output(OrePrefix.spindle, m)
                .tier(3).duration(20)
                .buildAndRegister()
            registry.builder()
                .input(OrePrefix.largePlate, m)
                .input(OrePrefix.block, m, 8)
                .output(OrePrefix.grindingHead, m)
                .tier(3).duration(20)
                .buildAndRegister()
            registry.builder()
                .input(OrePrefix.largePlate, m)
                .input(OrePrefix.plate, m, 8)
                .output(OrePrefix.cuttingHead, m)
                .tier(3).duration(20)
                .buildAndRegister()
        }
        registry.builder()
            .input(MetaItemClayParts.CeeCircuit)
            .input(OrePrefix.plate, CMaterials.industrialClay)
            .output(MetaItemClayParts.CEE)
            .CEt(ClayEnergy.micro(80)).duration(20)
            .buildAndRegister()
        registry.builder()
            .input(MetaItemClayParts.PrecisionCircuit)
            .input(MetaItemClayParts.EnergizedClayDust, 32)
            .output(MetaItemClayParts.IntegratedCircuit)
            .tier(6).CEt(ClayEnergy.milli(100)).duration(1200)
            .buildAndRegister()
        registry.builder()
            .input(MetaItemClayParts.IntegratedCircuit)
            .input(MetaItemClayParts.CEE)
            .output(MetaItemClayParts.LaserParts)
            .tier(6).CEt(ClayEnergy.milli(100)).duration(20)
            .buildAndRegister()
        registry.builder()
            .input(MetaItemClayParts.IntegratedCircuit)
            .input(OrePrefix.dust, CMaterials.beryllium, 8)
            .output(MetaItemClayParts.SynchronousParts)
            .tier(6).duration(432_000)
            .buildAndRegister()
        //endregion

        /* Pan Cable */
        registry.builder()
            .input(OrePrefix.gem, CMaterials.pureAntimatter, 3)
            .input(OrePrefix.block, CMarkerMaterials.glass, 2)
            .output(ClayiumBlocks.PAN_CABLE, 12)
            .tier(10).CEt(ClayEnergy.of(1000)).duration(2)
            .buildAndRegister()

        registry.builder()
            .input(Blocks.STONEBRICK)
            .input(Blocks.VINE)
            .output(ItemStack(Blocks.STONEBRICK, 1, 1))
            .tier(6).duration(20)
            .buildAndRegister()
        registry.builder()
            .input(Items.LEATHER, 4)
            .input(Items.STRING, 16)
            .output(Items.SADDLE)
            .tier(10).CEt(ClayEnergy.of(1000)).duration(6000)
            .buildAndRegister()
        registry.builder()
            .input(Items.PAPER, 2)
            .input(Items.STRING, 4)
            .output(Items.NAME_TAG)
            .tier(10).CEt(ClayEnergy.of(1000)).duration(600)
            .buildAndRegister()

        /* Clay Markers */
        registry.builder()
            .input(Blocks.CLAY)
            .input(MetaItemClayParts.PrecisionCircuit)
            .output(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.NO_EXTEND))
            .tier(6).CEt(ClayEnergy.of(1)).duration(480)
            .buildAndRegister()
        registry.builder()
            .input(OrePrefix.block, CMaterials.denseClay)
            .input(MetaItemClayParts.ClayCore)
            .output(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.EXTEND_TO_GROUND))
            .tier(6).CEt(ClayEnergy.of(10)).duration(480)
            .buildAndRegister()
        registry.builder()
            .input(OrePrefix.block, CMaterials.compressedClay)
            .input(MetaItemClayParts.ClayCore)
            .output(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.EXTEND_TO_SKY))
            .tier(6).CEt(ClayEnergy.of(10)).duration(480)
            .buildAndRegister()
        registry.builder()
            .input(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.EXTEND_TO_GROUND))
            .input(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.EXTEND_TO_SKY))
            .output(ClayiumBlocks.CLAY_MARKER.getItem(ClayMarkerType.ALL_HEIGHT))
            .tier(6).CEt(ClayEnergy.of(10)).duration(480)
            .buildAndRegister()
    }
}