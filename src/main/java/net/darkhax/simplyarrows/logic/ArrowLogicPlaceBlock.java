package net.darkhax.simplyarrows.logic;

import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.simplyarrows.entity.EntitySimpleArrow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class ArrowLogicPlaceBlock implements IArrowLogic {

    private final IBlockState placementState;
    private final ItemStack fallbackItem;

    public ArrowLogicPlaceBlock (IBlockState state) {

        this(state, ItemStack.EMPTY);
    }

    public ArrowLogicPlaceBlock (IBlockState state, ItemStack fallback) {

        this.placementState = state;
        this.fallbackItem = fallback;
    }

    @Override
    public void onBlockHit (EntitySimpleArrow arrow, RayTraceResult hit) {

        final BlockPos pos = hit.getBlockPos().offset(hit.sideHit);

        if (arrow.getEntityWorld().isAirBlock(pos) || arrow.getEntityWorld().getBlockState(pos).getBlock().isReplaceable(arrow.getEntityWorld(), pos)) {

            arrow.getEntityWorld().setBlockState(pos, this.placementState);
            arrow.setDead();
        }

        else if (this.fallbackItem.isEmpty()) {

            StackUtils.dropStackInWorld(arrow.getEntityWorld(), pos, this.fallbackItem);
            arrow.setDead();
        }
    }

    public IBlockState getPlacementState () {

        return this.placementState;
    }

    public ItemStack getFallbackItem () {

        return this.fallbackItem;
    }
}
