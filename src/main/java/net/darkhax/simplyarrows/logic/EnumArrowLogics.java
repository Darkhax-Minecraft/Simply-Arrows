package net.darkhax.simplyarrows.logic;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

public enum EnumArrowLogics {

    ARROW_FLAME(new ArrowLogicPlaceBlock(Blocks.FIRE)),
    ARROW_CAKE(new ArrowLogicPlaceBlock(Blocks.CAKE, Items.CAKE)),
    ARROW_TORCH(new ArrowLogicPlaceBlock(Blocks.TORCH, Blocks.TORCH)),
    ARROW_LOVE(new ArrowLogicLove()),
    ARROW_TNT(new ArrowLogicExplode(4f)),
    ARROW_ENDER(new ArrowLogicEnder());

    private final IArrowLogic logic;

    EnumArrowLogics (IArrowLogic logic) {

        this.logic = logic;
    }

    public IArrowLogic getLogic () {

        return logic;
    }

}
