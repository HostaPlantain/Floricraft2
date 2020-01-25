package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.block.doll.BlockDollIronSit;
import com.hosta.Floricraft2.block.doll.BlockDollPlayer;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.item.doll.ItemMessage;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleDolls implements IModule, IModuleRecipe {

	// Item Usable
	// public static final Item ITEM_BALLON;
	public static final Item	LETTER_BOTTLED		= new ItemBasic("letter_bottled").setMaxStackSize(1);
	public static final Item	LETTER_RECEIVED		= new ItemMessage("letter_received");

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
	public void registerItems()
	{
		this.register(LETTER_BOTTLED, LETTER_RECEIVED);
	}

	@Override
	public void registerRecipes()
	{
		this.register
		(
				shapelessRecipe(null, LETTER_RECEIVED, LETTER_BOTTLED),
				shapedRecipe(null, new ItemStack(DOLL_IRON), " p ", "iii", " i ", 'p', Blocks.PUMPKIN, 'i', Items.IRON_INGOT),
				shapedRecipe(null, new ItemStack(DOLL_PLAYER), " h ", "iii", " i ", 'h', new ItemStack(Items.SKULL, 1, OreDictionary.WILDCARD_VALUE), 'i', Items.IRON_INGOT)
		);
	}
}
