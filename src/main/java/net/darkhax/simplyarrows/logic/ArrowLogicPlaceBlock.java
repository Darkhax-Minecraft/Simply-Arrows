package net.darkhax.simplyarrows.logic;

import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ArrowLogicPlaceBlock implements IArrowLogic {

    private final Block block;
    private final int meta;
    private final ItemStack fallbackItem;

    public ArrowLogicPlaceBlock (Block block) {

        this(block, 0);
    }

    public ArrowLogicPlaceBlock (Block block, Block blockItem) {

        this(block, Item.getItemFromBlock(blockItem));
    }

    public ArrowLogicPlaceBlock (Block block, Item item) {

        this(block, new ItemStack(item));
    }

    public ArrowLogicPlaceBlock (Block block, ItemStack fallback) {

        this(block, 0, fallback);
    }

    public ArrowLogicPlaceBlock (Block block, int meta) {

        this(block, meta, ItemStack.EMPTY);
    }

    public ArrowLogicPlaceBlock (Block block, int meta, ItemStack fallback) {

        this.block = block;
        this.meta = meta;
        this.fallbackItem = fallback;
    }

    @Override
    public void onBlockHit (EntitySimpleArrow arrow, RayTraceResult hit) {

        final World world = arrow.getEntityWorld();
        final BlockPos pos = hit.getBlockPos().offset(hit.sideHit);

        if (world.getBlockState(pos).getBlock().isReplaceable(world, pos) && this.block.canPlaceBlockOnSide(world, pos, hit.sideHit)) {

            final EntityLivingBase shooter = arrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase) arrow.shootingEntity : null;
            arrow.getEntityWorld().setBlockState(pos, this.block.getStateForPlacement(world, pos, hit.sideHit, 0.5f, 0.5f, 0.5f, this.meta, shooter, EnumHand.MAIN_HAND));
            arrow.setDead();
        }

        else if (!this.fallbackItem.isEmpty()) {

            StackUtils.dropStackInWorld(world, pos, this.fallbackItem);
            arrow.setDead();
        }
    }

    public ItemStack getFallbackItem () {

        return this.fallbackItem;
    }

    public Block getBlock () {

        return this.block;
    }

    public int getMeta () {

        return this.meta;
    }
}
