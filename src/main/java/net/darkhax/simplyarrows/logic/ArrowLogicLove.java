package net.darkhax.simplyarrows.logic;

import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class ArrowLogicLove implements IArrowLogic {

    @Override
    public void onEntityHit (EntitySimpleArrow arrow, EntityLivingBase entity) {

        if (entity instanceof EntityAnimal) {

            final EntityPlayer player = arrow.shootingEntity instanceof EntityPlayer ? (EntityPlayer) arrow.shootingEntity : null;
            final EntityAnimal animal = (EntityAnimal) entity;

            animal.setRevengeTarget(null);
            animal.setInLove(player);
        }
    }
}