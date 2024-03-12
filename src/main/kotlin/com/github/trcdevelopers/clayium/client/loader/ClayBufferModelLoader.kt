package com.github.trcdevelopers.clayium.client.loader

import com.github.trcdevelopers.clayium.client.model.buffer.ClayBufferModel
import com.github.trcdevelopers.clayium.client.model.buffer.ClayBufferPipeModel
import com.github.trcdevelopers.clayium.common.Clayium
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.IResourceManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ICustomModelLoader
import net.minecraftforge.client.model.IModel
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

internal fun ModelResourceLocation.getVariantValue(variantName: String): String? {
    if (!this.toString().contains(variantName)) return null
    return this.variant.replaceBefore("$variantName=", "")
        .replaceAfter(",", "")
        .replace("$variantName=", "").replace(",", "")
}

@SideOnly(Side.CLIENT)
object ClayBufferModelLoader : ICustomModelLoader {

    private val alphabetAndUnderline = Regex("[A-z_]+")

    override fun onResourceManagerReload(resourceManager: IResourceManager) {}

    override fun accepts(modelLocation: ResourceLocation): Boolean {
        if (!(modelLocation.namespace == Clayium.MOD_ID
                && modelLocation is ModelResourceLocation
                && "is_pipe" in modelLocation.variant)) {
            return false
        }
        val registryName = modelLocation.path
        return registryName.startsWith("clay_buffer")
    }

    override fun loadModel(modelLocation: ResourceLocation): IModel {
        val modelResourceLocation = modelLocation as ModelResourceLocation
        val registryName = modelLocation.path
        val isPipe = modelResourceLocation.getVariantValue("is_pipe")?.toBoolean() ?: false
        val tier = registryName.replace(alphabetAndUnderline, "").toInt()
        return if (isPipe) ClayBufferPipeModel(tier) else ClayBufferModel(tier)
    }
}