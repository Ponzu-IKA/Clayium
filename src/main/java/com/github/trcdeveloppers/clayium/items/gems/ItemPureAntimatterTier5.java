package com.github.trcdeveloppers.clayium.items.gems;

import com.github.trcdeveloppers.clayium.interfaces.IColored;
import com.github.trcdeveloppers.clayium.items.ClayiumItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;

import static com.github.trcdeveloppers.clayium.creativetab.ClayiumCreativeTab.CLAYIUM;

@SuppressWarnings("unused")
@com.github.trcdeveloppers.clayium.annotation.Item(registryName = "pure_antimatter_tier5")
public class ItemPureAntimatterTier5 extends Item implements ClayiumItems.ClayiumItem, IColored {

    public ItemPureAntimatterTier5() {
        super();
        setCreativeTab(CLAYIUM);
    }

    @Override
    public IItemColor getColor() {
        return ((stack, tintIndex) -> tintIndex == 0 ? 0x520829 : (tintIndex == 1 ? 0x7D7D00 : 0xFFFFFF));
    }
}