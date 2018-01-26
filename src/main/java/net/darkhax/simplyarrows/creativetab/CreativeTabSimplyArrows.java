package net.darkhax.simplyarrows.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabSimplyArrows extends CreativeTabs {

    public CreativeTabSimplyArrows () {

        super("simplyarrows");
    }

    @Override
    public ItemStack getTabIconItem () {

        return new ItemStack(Items.ARROW);
    }
}
