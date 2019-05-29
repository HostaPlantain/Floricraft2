package com.hosta.Floricraft2.block.doll;

import com.hosta.Floricraft2.client.render.tileentity.TileEntityDollIronSitRenderer;
import com.hosta.Floricraft2.tileentity.doll.TileEntityDollIronSit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDollIronSit extends BlockDoll {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);

	public BlockDollIronSit(String name)
	{
		super(name, TileEntityDollIronSit.class);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDollIronSit();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TileEntitySpecialRenderer<? extends TileEntity> getCustomRenderer()
	{
		return new TileEntityDollIronSitRenderer();
	}

}
