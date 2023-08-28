package com.github.trcdeveloppers.clayium.items.gems;

import com.github.trcdeveloppers.clayium.annotation.CItem;
import com.github.trcdeveloppers.clayium.interfaces.ITiered;
import com.github.trcdeveloppers.clayium.items.ClayiumItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.github.trcdeveloppers.clayium.creativetab.ClayiumCreativeTab.CLAYIUM;

@SuppressWarnings("unused")
@CItem(registryName = "pure_antimatter_tier6")
public class ItemPureAntimatterTier6 extends Item implements ClayiumItems.ClayiumItem, IItemColor, ITiered {

    public ItemPureAntimatterTier6() {
        super();
        setCreativeTab(CLAYIUM);
    }

    @Override
    @ParametersAreNonnullByDefault
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return tintIndex == 0 ? 0x6E0727 : (tintIndex == 1 ? 0x969600 : 0xFFFFFF);
    }

    @Override
    public int getTier() {
        return 12;
    }
}