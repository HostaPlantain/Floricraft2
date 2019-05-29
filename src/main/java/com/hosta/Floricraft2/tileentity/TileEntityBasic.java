package com.hosta.Floricraft2.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public abstract class TileEntityBasic extends TileEntity {

	public IBlockState getBlockState()
	{
		return this.getWorld().getBlockState(this.pos);
	}

	public Block getBlock()
	{
		return this.getBlockState().getBlock();
	}

	@SuppressWarnings("deprecation")
	public AxisAlignedBB getAxisAlignedBB()
	{
		return this.getBlock().getBoundingBox(this.getBlockState(), this.world, this.pos).offset(this.pos);
	}
}
