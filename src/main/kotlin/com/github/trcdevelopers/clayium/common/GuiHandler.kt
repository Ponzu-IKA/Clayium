package com.github.trcdevelopers.clayium.common

import com.github.trcdevelopers.clayium.client.gui.GuiClayWorkTable
import com.github.trcdevelopers.clayium.client.gui.GuiSingle2SingleMachine
import com.github.trcdevelopers.clayium.common.gui.ContainerClayWorkTable
import com.github.trcdevelopers.clayium.common.blocks.clayworktable.TileClayWorkTable
import com.github.trcdevelopers.clayium.common.blocks.machine.tile.TileClayBuffer
import com.github.trcdevelopers.clayium.common.blocks.machine.tile.TileSingle2SingleMachine
import com.github.trcdevelopers.clayium.common.gui.ContainerSingle2SingleMachine
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object GuiHandler : IGuiHandler {

    const val CLAY_WORK_TABLE = 1
    const val SINGLE_2_SINGLE = 3

    override fun getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z)) ?: return null
        return when (id) {
            CLAY_WORK_TABLE -> ContainerClayWorkTable(player.inventory, tile as TileClayWorkTable)
            SINGLE_2_SINGLE -> ContainerSingle2SingleMachine(player.inventory, tile as TileSingle2SingleMachine)
            else -> null
        }
    }

    override fun getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tile = world.getTileEntity(BlockPos(x, y, z)) ?: return null
        return when (id) {
            CLAY_WORK_TABLE -> GuiClayWorkTable(player.inventory, tile as TileClayWorkTable)
            SINGLE_2_SINGLE -> GuiSingle2SingleMachine(player.inventory, tile as TileSingle2SingleMachine)
            else -> null
        }
    }
}
