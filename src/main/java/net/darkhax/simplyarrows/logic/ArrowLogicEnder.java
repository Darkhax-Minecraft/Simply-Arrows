package net.darkhax.simplyarrows.logic;

import net.darkhax.bookshelf.util.EntityUtils;
import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class ArrowLogicEnder implements IArrowLogic {

    @Override
    public void onEntityHit (EntitySimpleArrow arrow, EntityLivingBase entity) {

        if (arrow.shootingEntity instanceof EntityLivingBase) {

            EntityUtils.enderTeleport((EntityLivingBase) arrow.shootingEntity, entity.dimension, entity.posX, entity.posY, entity.posZ, 0);
        }
    }

    @Override
    public void onBlockHit (EntitySimpleArrow arrow, RayTraceResult hit) {

        if (arrow.shootingEntity instanceof EntityLivingBase) {

            final BlockPos pos = hit.getBlockPos().offset(hit.sideHit);
            EntityUtils.enderTeleport((EntityLivingBase) arrow.shootingEntity, arrow.dimension, pos.getX(), pos.getY(), pos.getZ(), 0);
        }
    }
}