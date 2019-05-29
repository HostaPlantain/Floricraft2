package com.hosta.Floricraft2.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockEntityRotaion extends BlockEntityContainer {

	private static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);

	protected BlockEntityRotaion(String name, Material materialIn, Class<? extends TileEntity> te)
	{
		super(name, materialIn, te);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));
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
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.CENTER_BIG;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		int i = MathHelper.floor(placer.rotationYaw * 16.0F / 360.0F + 8.5F) & 15;
		return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(i));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ROTATION);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		int i = rot.rotate(((Integer) state.getValue(ROTATION)).intValue(), 16);
		return state.withProperty(ROTATION, Integer.valueOf(i));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		int i = mirrorIn.mirrorRotation(((Integer) state.getValue(ROTATION)).intValue(), 16);
		return state.withProperty(ROTATION, Integer.valueOf(i));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { ROTATION });
	}
}
