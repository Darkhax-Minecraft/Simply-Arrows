package net.darkhax.simplyarrows.items;

import java.util.List;

import javax.annotation.Nullable;

import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.darkhax.simplyarrows.logic.EnumArrowLogics;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArrowBase extends ItemArrow {

    private boolean infinity = false;
    private double damage = 2;
    private int knockback = 0;
    private boolean flaming = false;
    private ItemStack dropItem = ItemStack.EMPTY;
    private EnumArrowLogics enumLogic;

    public ItemArrowBase () {

        this.dropItem = new ItemStack(this);
    }

    @Override
    public EntityArrow createArrow (World world, ItemStack stack, EntityLivingBase shooter) {

        final EntitySimpleArrow arrow = new EntitySimpleArrow(world, this.dropItem.copy(), shooter);
        arrow.setLogic(this.enumLogic);
        arrow.setDamage(this.damage);
        arrow.setKnockbackStrength(this.knockback);

        if (this.flaming) {

            arrow.setFire(100);
        }

        return arrow;
    }

    @Override
    public boolean isInfinite (ItemStack stack, ItemStack bow, EntityPlayer player) {

        final int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, bow);
        return enchant <= 0 ? false : this.infinity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        tooltip.add(TextFormatting.BLUE + I18n.format("tooltip.simplyarrows.damage", this.getDamage()));

        if (this.knockback > 0) {

            tooltip.add(TextFormatting.BLUE + I18n.format("tooltip.simplyarrows.knockback", this.getKnockback()));
        }
    }

    public boolean isInfinity () {

        return this.infinity;
    }

    public ItemArrowBase setInfinity (boolean infinity) {

        this.infinity = infinity;
        return this;
    }

    public double getDamage () {

        return this.damage;
    }

    public ItemArrowBase setDamage (double damage) {

        this.damage = damage;
        return this;
    }

    public int getKnockback () {

        return this.knockback;
    }

    public ItemArrowBase setKnockback (int knockback) {

        this.knockback = knockback;
        return this;
    }

    public boolean isFlaming () {

        return this.flaming;
    }

    public ItemArrowBase setFlaming (boolean flaming) {

        this.flaming = flaming;
        return this;
    }

    public ItemStack getDropItem () {

        return this.dropItem;
    }

    public ItemArrowBase setDropItem (ItemStack dropItem) {

        this.dropItem = dropItem;
        return this;
    }

    public EnumArrowLogics getLogic () {

        return this.enumLogic;
    }

    public ItemArrowBase setLogic (EnumArrowLogics enumLogic) {

        this.enumLogic = enumLogic;
        return this;
    }
}
