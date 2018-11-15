package com.hosta.Floricraft2.block;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class BlockBasicCrops extends BlockCrops {

	private final Item SEED;
	private final Item CROP;
	
	public BlockBasicCrops(String unlocalizedName, Item seed, Item crop)
	{
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.SEED = seed;
		this.CROP = crop;
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}
	
	@Override
	protected Item getSeed()
	{
		return this.SEED;
	}
	
	@Override
	protected Item getCrop()
	{
		return this.CROP;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		int i = super.quantityDropped(state, fortune, random);
		return isMaxAge(state) ? i + random.nextInt(fortune + 2) : i;
	}
}
