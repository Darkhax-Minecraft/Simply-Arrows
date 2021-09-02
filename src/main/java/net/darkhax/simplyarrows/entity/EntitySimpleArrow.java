package net.darkhax.simplyarrows.entity;

import io.netty.buffer.ByteBuf;
import net.darkhax.simplyarrows.logic.IArrowLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySimpleArrow extends EntityTippedArrow implements IEntityAdditionalSpawnData {

    private ItemStack arrowStack;
    private IArrowLogic logic;

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
        return compound;
    }

    @Override
    public void readFromNBT (NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.arrowStack = new ItemStack(compound.getCompoundTag("ArrowStack"));
    }

    @Override
    public void arrowHit (EntityLivingBase living) {

        super.arrowHit(living);

        if (this.logic != null) {

            this.logic.onEntityHit(this, living);
        }
    }

    @Override
    public void onUpdate () {

        super.onUpdate();

        if (!this.getEntityWorld().isRemote && this.inGround && this.logic != null) {

            final Vec3d vecCurrent = new Vec3d(this.posX, this.posY, this.posZ);
            final Vec3d vecProjected = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            final RayTraceResult hit = this.world.rayTraceBlocks(vecCurrent, vecProjected, false, true, false);

            if (hit != null && hit.typeOfHit == Type.BLOCK) {

                this.logic.onBlockHit(this, hit);
            }
        }
    }

    public IArrowLogic getLogic () {

        return this.logic;
    }

    public void setLogic (IArrowLogic logic) {

        this.logic = logic;
    }

    @Override
    public void writeSpawnData(ByteBuf data) {

        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data) {

        final Entity shootingEntity = world.getEntityByID(data.readInt());

        if (shootingEntity instanceof EntityLivingBase) {

            this.shootingEntity = shootingEntity;
        }
    }
}