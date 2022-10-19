package net.darkhax.simplyarrows.entity;

import javax.annotation.Nullable;

import org.apache.commons.lang3.EnumUtils;

import io.netty.buffer.ByteBuf;
import net.darkhax.simplyarrows.logic.EnumArrowLogics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySimpleArrow extends EntityTippedArrow implements IEntityAdditionalSpawnData {

    private ItemStack arrowStack;
    @Nullable
    private EnumArrowLogics enumLogic;

    public EntitySimpleArrow (World world) {

        super(world);
    }

    public EntitySimpleArrow (World world, ItemStack stack, EntityLivingBase shooter) {

        super(world, shooter);
        this.arrowStack = stack;
    }

    @Override
    protected ItemStack getArrowStack () {

        return this.arrowStack;
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound) {

        super.writeToNBT(compound);
        compound.setTag("ArrowStack", this.arrowStack.writeToNBT(new NBTTagCompound()));
        // Enum names are used as they likely to stay consistent between different mod versions
        if (enumLogic != null) {
            compound.setTag("ArrowLogic", new NBTTagString(enumLogic.name()));
        }
        return compound;
    }

    @Override
    public void readFromNBT (NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.arrowStack = new ItemStack(compound.getCompoundTag("ArrowStack"));
        // Enum names are used as they likely to stay consistent between different mod versions
        this.enumLogic = EnumUtils.getEnum(EnumArrowLogics.class, compound.getString("ArrowLogic"));
    }

    @Override
    public void arrowHit (EntityLivingBase living) {

        super.arrowHit(living);

        if (this.enumLogic != null) {

            this.enumLogic.getLogic().onEntityHit(this, living);
        }
    }

    @Override
    public void onUpdate () {

        super.onUpdate();

        if (!this.getEntityWorld().isRemote && this.inGround && this.enumLogic != null) {

            final Vec3d vecCurrent = new Vec3d(this.posX, this.posY, this.posZ);
            final Vec3d vecProjected = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            final RayTraceResult hit = this.world.rayTraceBlocks(vecCurrent, vecProjected, false, true, false);

            if (hit != null && hit.typeOfHit == Type.BLOCK) {

                this.enumLogic.getLogic().onBlockHit(this, hit);
            }
        }
    }

    /** Always returns -1 (no custom color) */
    @Override
    public int getColor () {

        return -1;
    }

    @Nullable
    public EnumArrowLogics getLogic () {

        return this.enumLogic;
    }

    public void setLogic (EnumArrowLogics enumLogic) {

        this.enumLogic = enumLogic;
    }

    @Override
    public void writeSpawnData (ByteBuf data) {

        // Ordinals are used as less data has to be transfered. -1 indicates that the enumLogic is null.
        data.writeInt(enumLogic != null ? enumLogic.ordinal() : -1);
        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData (ByteBuf data) {

        // Ordinals are used as less data has to be transfered. -1 indicates that the enumLogic is null.
        int enumLogicOrdinal = data.readInt();

        if (enumLogicOrdinal >= 0) {

            enumLogic = EnumArrowLogics.values()[enumLogicOrdinal];
        }

        final Entity shootingEntity = world.getEntityByID(data.readInt());

        if (shootingEntity instanceof EntityLivingBase) {

            this.shootingEntity = shootingEntity;
        }
    }
}