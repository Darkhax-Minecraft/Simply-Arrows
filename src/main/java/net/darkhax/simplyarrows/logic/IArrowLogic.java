package net.darkhax.simplyarrows.logic;

import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;

public interface IArrowLogic {

    default void onEntityHit (EntitySimpleArrow arrow, EntityLivingBase entity) {

    }

    default void onBlockHit (EntitySimpleArrow arrow, RayTraceResult hit) {

    }
}