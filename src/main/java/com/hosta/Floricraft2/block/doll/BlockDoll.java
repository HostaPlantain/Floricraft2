package com.hosta.Floricraft2.block.doll;

import javax.annotation.Nullable;

import com.hosta.Floricraft2.block.BlockEntityRotaion;
import com.hosta.Floricraft2.tileentity.doll.TileEntityDoll;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockDoll extends BlockEntityRotaion {

	public BlockDoll(String name, Class<? extends TileEntity> te)
	{
		super(name, Material.CLOTH, te);
		this.setHardness(0.0F).setResistance(0.0F).setLightOpacity(0);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate)
	{
		TileEntityDoll doll = this.getTileEntityDoll(world, pos);
		if (doll != null)
		{
			if (doll.getDisplayedItem() != null)
			{
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), doll.getDisplayedItem()));
			}
		}

		super.breakBlock(world, pos, blockstate);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntityDoll doll = this.getTileEntityDoll(worldIn, pos);
		if (doll != null)
		{
			doll.onRightClick(playerIn, playerIn.getHeldItem(hand), hand);
			return true;
		}
		return false;
	}

	@Nullable
	public TileEntityDoll getTileEntityDoll(IBlockAccess world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityDoll)
		{
			TileEntityDoll doll = (TileEntityDoll) te;
			return doll;
		}
		return null;
	}
}
