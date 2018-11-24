package com.hosta.Floricraft2.block;

import java.util.Random;

import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockBasicOre extends BlockBasic {

	private final Item ITEM_DROP;
	
	public BlockBasicOre(String name)	{this(name, null);}
	public BlockBasicOre(String name, Item drop)
	{
		super(name, Material.ROCK);
		this.ITEM_DROP = drop != null ? drop : Item.getItemFromBlock(this);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ITEM_DROP;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		int i = 1;
		if (ITEM_DROP != Item.getItemFromBlock(this))
		{
			i += fortune;
		}
		return this.quantityDropped(random) * i;
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		int i = 1;
		if (ITEM_DROP != Item.getItemFromBlock(this))
		{
			i += random.nextInt(3);
		}
		return i;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		int i = 0;
		Random rand = Helper.getRand(world);
        
		if (this == ModuleItems.ORE_SALT)
		{
			i += MathHelper.getInt(rand, 0, 2);
		}
		
		return i;
	}
}
