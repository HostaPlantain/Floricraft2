package com.hosta.Floricraft2.block.flower;

import javax.annotation.Nullable;

import com.hosta.Floricraft2.block.BlockBaseHorizontal;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStack extends BlockBaseHorizontal {

	protected static final AxisAlignedBB STACK_AABB[] = new AxisAlignedBB[] {
			new AxisAlignedBB(0.2D, 0.1D, 0.9375D, 0.8D, 0.9D, 1.0D),
			new AxisAlignedBB(0.0D, 0.1D, 0.2D, 0.0625D, 0.9D, 0.8D),
			new AxisAlignedBB(0.2D, 0.1D, 0.0D, 0.8D, 0.9D, 0.0625D),
			new AxisAlignedBB(0.9375D, 0.1D, 0.2D, 1.0D, 0.9D, 0.8D) };

	public BlockStack(String name)
	{
		super(name, Material.LEAVES);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
		this.setHardness(0.0F).setResistance(0.0F);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(FACING))
		{
			case SOUTH:
			default:
				return STACK_AABB[0];
			case WEST:
				return STACK_AABB[1];
			case NORTH:
				return STACK_AABB[2];
			case EAST:
				return STACK_AABB[3];
		}
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		if (side.getAxis().isHorizontal())
		{
			return this.canAttachStackOn(worldIn.getBlockState(pos.offset(side.getOpposite())));
		}
		else
		{
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		this.checkAndDropBlock(worldIn, pos, state);
	}

	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!this.canBlockStay(worldIn, pos, state))
		{
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		}
	}

	protected boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState stateOn = worldIn.getBlockState(pos.offset(state.getValue(FACING)));
		return this.canAttachStackOn(stateOn);
	}

	protected boolean canAttachStackOn(IBlockState state)
	{
		return state.isFullCube() && state.getMaterial().blocksMovement();
	}
}
