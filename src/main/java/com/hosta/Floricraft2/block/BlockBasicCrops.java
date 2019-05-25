package com.hosta.Floricraft2.block;

import java.util.Random;

import com.hosta.Floricraft2.module.ModuleCrops;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class BlockBasicCrops extends BlockCrops {

	public BlockBasicCrops(String unlocalizedName)
	{
		super();
		this.setUnlocalizedName(unlocalizedName);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}

	@Override
	protected Item getSeed()
	{
		return this == ModuleCrops.CROP_HEMP ? ModuleCrops.SEED_HEMP : super.getSeed();
	}

	@Override
	protected Item getCrop()
	{
		return this == ModuleCrops.CROP_HEMP ? ModuleCrops.HEMP_YARN : super.getCrop();
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		int i = super.quantityDropped(state, fortune, random);
		return isMaxAge(state) ? i + random.nextInt(fortune + 2) : i;
	}
}
