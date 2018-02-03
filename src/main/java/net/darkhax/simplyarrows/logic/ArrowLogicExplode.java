package net.darkhax.simplyarrows.logic;

import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class ArrowLogicExplode implements IArrowLogic {

    private final float strength;
    private final boolean fire;
    private final boolean terrain;

    public ArrowLogicExplode (float strength) {

        this(strength, false, true);
    }

    public ArrowLogicExplode (float strength, boolean fire, boolean terrain) {

        this.strength = strength;
        this.fire = fire;
        this.terrain = terrain;
    }

    @Override
    public void onEntityHit (EntitySimpleArrow arrow, EntityLivingBase entity) {

        this.createExplosion(arrow, entity.getPosition());
    }

    @Override
    public void onBlockHit (EntitySimpleArrow arrow, RayTraceResult hit) {

        this.createExplosion(arrow, hit.getBlockPos().offset(hit.sideHit));
    }

    private void createExplosion (EntitySimpleArrow arrow, BlockPos pos) {

        arrow.getEntityWorld().newExplosion(arrow.shootingEntity, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.05f, this.strength, this.fire, this.terrain);
        arrow.setDead();
    }
}