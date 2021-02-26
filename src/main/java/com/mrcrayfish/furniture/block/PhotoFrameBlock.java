package com.mrcrayfish.furniture.block;


import com.mrcrayfish.furniture.tileentity.PhotoFrameTileEntity;
import com.mrcrayfish.furniture.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Grillo78
 */
public class PhotoFrameBlock extends FurnitureHorizontalWaterloggedBlock{

    public final Map<BlockState, VoxelShape> SHAPES = new HashMap<>();

    public PhotoFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new PhotoFrameTileEntity();
    }

    private VoxelShape getShape(BlockState state)
    {
        return SHAPES.computeIfAbsent(state, state1 -> {
            //TODO
            final VoxelShape[] BOXES = VoxelShapeHelper.getRotatedShapes(VoxelShapeHelper.rotate(Stream.of(
                    Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
                    Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
                    Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
                    Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
                    Block.makeCuboidShape(1, 1, 0, 15, 15, 0.5),
                    Block.makeCuboidShape(11, 9.5, 0, 14, 14.5, 0.6),
                    Block.makeCuboidShape(9, 2.5, 0, 12, 7.5, 0.6),
                    Block.makeCuboidShape(4, 8.5, 0, 7, 13.5, 0.6),
                    Block.makeCuboidShape(2, 1.5, 0, 5, 6.5, 0.6)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get(), Direction.SOUTH));
            return BOXES[state.get(DIRECTION).getHorizontalIndex()];
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return this.getShape(state);
    }
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return super.isValidPosition(state,worldIn,pos);
    }
}
