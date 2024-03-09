package com.github.trcdevelopers.clayium.common

import com.github.trcdevelopers.clayium.common.blocks.ClayiumBlocks
import com.github.trcdevelopers.clayium.common.blocks.clay.ItemBlockCompressedClay
import com.github.trcdevelopers.clayium.common.blocks.clay.ItemBlockEnergizedClay
import com.github.trcdevelopers.clayium.common.blocks.machine.BlockMachine
import com.github.trcdevelopers.clayium.common.blocks.machine.MachineBlocks
import com.github.trcdevelopers.clayium.common.blocks.machine.TileEntityMachine
import com.github.trcdevelopers.clayium.common.blocks.machine.claybuffer.TileClayBuffer
import com.github.trcdevelopers.clayium.common.blocks.machine.clayworktable.TileClayWorkTable
import com.github.trcdevelopers.clayium.common.interfaces.IShiftRightClickable
import com.github.trcdevelopers.clayium.common.items.ClayiumItems
import com.github.trcdevelopers.clayium.common.items.metaitem.MetaPrefixItem
import com.github.trcdevelopers.clayium.common.recipe.loader.CRecipeLoader
import com.github.trcdevelopers.clayium.common.unification.OrePrefix
import com.github.trcdevelopers.clayium.common.worldgen.ClayOreGenerator
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.registries.IForgeRegistry

open class CommonProxy {
    open fun preInit(event: FMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(Clayium.proxy)
        this.registerTileEntities()
        GameRegistry.registerWorldGenerator(ClayOreGenerator(), 0)
        NetworkRegistry.INSTANCE.registerGuiHandler(Clayium.INSTANCE, GuiHandler)
    }

    open fun init(event: FMLInitializationEvent) {
    }

    open fun postInit(event: FMLPostInitializationEvent) {
        CRecipeLoader.load()
    }

    @SubscribeEvent
    open fun registerBlocks(event: RegistryEvent.Register<Block>) {
        ClayiumBlocks.registerBlocks(event, Side.SERVER)

        event.registry.register(ClayiumBlocks.COMPRESSED_CLAY)
        event.registry.register(ClayiumBlocks.ENERGIZED_CLAY)

        for (machines in MachineBlocks.ALL_MACHINES) {
            for (machine in machines.value) {
                event.registry.register(machine.value)
            }
        }
    }

    @SubscribeEvent
    open fun registerItems(event: RegistryEvent.Register<Item>) {
        val registry = event.registry

        for (orePrefix in OrePrefix.entries) {
            val metaPrefixItem = MetaPrefixItem.create("meta_${orePrefix.snake}", orePrefix)
            registry.register(metaPrefixItem)
            metaPrefixItem.registerSubItems()
        }

        ClayiumItems.registerItems(event, Side.SERVER)
        registry.register(ItemBlockCompressedClay(ClayiumBlocks.COMPRESSED_CLAY))
        registry.register(ItemBlockEnergizedClay(ClayiumBlocks.ENERGIZED_CLAY))

        for (machines in MachineBlocks.ALL_MACHINES) {
            val machineName = machines.key
            for (machine in machines.value) {
                registerMachineItemBlock(registry, machineName, machine.key, machine.value)
            }
        }
    }

    open fun registerItem(registry: IForgeRegistry<Item>, item: Item) {
        registry.register(item)
    }

    open fun registerMachineItemBlock(registry: IForgeRegistry<Item>, machineName: String, tier: Int, block: BlockMachine): Item {
        val itemBlock = ItemBlock(block).setRegistryName(block.registryName)
        registry.register(itemBlock)
        return itemBlock
    }

    open fun registerTileEntities() {
        GameRegistry.registerTileEntity(TileClayWorkTable::class.java, ResourceLocation(Clayium.MOD_ID, "TileClayWorkTable"))
        GameRegistry.registerTileEntity(TileClayBuffer::class.java, ResourceLocation(Clayium.MOD_ID, "TileClayBuffer"))
        GameRegistry.registerTileEntity(TileEntityMachine::class.java, ResourceLocation(Clayium.MOD_ID, "TileEntityMachine"))
    }

    // todo: move this to item
    @SubscribeEvent
    fun onBlockRightClicked(e: PlayerInteractEvent.RightClickBlock) {
        val world = e.world
        val blockState = world.getBlockState(e.pos)
        val block = blockState.block

        if (block is IShiftRightClickable && e.entityPlayer.isSneaking) {
            val (cancel, swing) = block.onShiftRightClicked(world, e.pos, blockState, e.entityPlayer, e.hand, e.face ?: return, e.hitVec.x.toFloat(), e.hitVec.y.toFloat(), e.hitVec.z.toFloat())
            e.isCanceled = cancel
            if (swing) e.entityPlayer.swingArm(e.hand)
        }
    }
}
