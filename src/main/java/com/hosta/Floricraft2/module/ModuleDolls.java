package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.block.doll.BlockDollIronSit;
import com.hosta.Floricraft2.block.doll.BlockDollPlayer;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleDolls implements IModule {

	// Item Usable
	// public static final Item ITEM_BALLON;

	// Doll
	public static final Block	DOLL_IRON	= new BlockDollIronSit("doll_iron");
	public static final Block	DOLL_PLAYER	= new BlockDollPlayer("doll_player");

	// Wether
	// public static final Block WEATHER_COCK;

	@Override
	public void registerBlocks()
	{
		this.register(DOLL_IRON, DOLL_PLAYER);
	}

	@Override
	public void registerRecipes()
	{
		this.register
		(
				shapedRecipe(null, new ItemStack(DOLL_IRON), " p ", "iii", " i ", 'p', Blocks.PUMPKIN, 'i', Items.IRON_INGOT),
				shapedRecipe(null, new ItemStack(DOLL_PLAYER), " h ", "iii", " i ", 'h', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE), 'i', Items.IRON_INGOT)
		);
	}
}
