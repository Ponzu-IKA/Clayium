package com.github.trc.clayium.api.capability.impl

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class EnergyStorageSerializable @JvmOverloads constructor(
    capacity: Int,
    maxReceive: Int,
    maxExtract: Int,
    energy: Int = 0,
) : EnergyStorage(capacity, maxReceive, maxExtract, energy), INBTSerializable<NBTTagCompound> {
    @JvmOverloads
    constructor(capacity: Int, maxTransfer: Int = capacity) : this(capacity, maxTransfer, maxTransfer)

    override fun serializeNBT(): NBTTagCompound {
        val data = NBTTagCompound()
        data.setInteger("energy", this.energy)
        return data
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        this.energy = nbt.getInteger("energy")
    }

    /**
     * should only be used for syncing (e.g. GUI)
     */
    @SideOnly(Side.CLIENT)
    fun setEnergy(energy: Int) {
        this.energy = energy
    }
}