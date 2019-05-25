package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.block.BlockBasic;
import com.hosta.Floricraft2.block.BlockBasicFalling;
import com.hosta.Floricraft2.block.BlockBasicOre;
import com.hosta.Floricraft2.item.ItemBasic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleMaterials implements IModule {

	// Ingot
	public static final Item	INGOT_TWINKLE	= new ItemBasic("ingot_twinkle");
	public static final Item	NUGGET_TWINKLE	= new ItemBasic("nugget_twinkle");
	public static final Block	BLOCK_TWINKLE	= new BlockBasic("block_twinkle", Material.IRON);

	// Salt
	public static final Item	DUST_SALT		= new ItemBasic("dust_salt");
	public static final Block	ORE_SALT		= new BlockBasicOre("ore_salt", DUST_SALT).setHardness(1.5F).setResistance(10.0F);
	public static final Block	BLOCK_SALT		= new BlockBasicFalling("block_salt").setHardness(0.5F);

	@Override
	public void registerBlocks()
	{
		register(BLOCK_TWINKLE, ORE_SALT, BLOCK_SALT);
	}

	@Override
	public void registerItems()
	{
		register(INGOT_TWINKLE, NUGGET_TWINKLE, DUST_SALT);
	}

	@Override
	public void registerOreDictionary()
	{
		// Ingot
		OreDictionary.registerOre("ingotTwinkle", INGOT_TWINKLE);
		OreDictionary.registerOre("nuggetTwinkle", NUGGET_TWINKLE);
		OreDictionary.registerOre("blockTwinkle", BLOCK_TWINKLE);

		// Salt
		OreDictionary.registerOre("dustSalt", DUST_SALT);
		OreDictionary.registerOre("itemSalt", DUST_SALT);
		OreDictionary.registerOre("oreSalt", ORE_SALT);
		OreDictionary.registerOre("blockSalt", BLOCK_SALT);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
				// Ingot
				compressRecipe(null, INGOT_TWINKLE, new ItemStack(NUGGET_TWINKLE), true),
				shapelessRecipe(null, new ItemStack(INGOT_TWINKLE, 9, 0), BLOCK_TWINKLE),
				shapelessRecipe(null, new ItemStack(NUGGET_TWINKLE, 9, 0), INGOT_TWINKLE),
				compressRecipe(null, BLOCK_TWINKLE, new ItemStack(INGOT_TWINKLE), true),
				// Salt
				shapelessRecipe(null, new ItemStack(DUST_SALT, 4, 0), BLOCK_SALT),
				shapedRecipe(null, BLOCK_SALT, "dd", "dd", 'd', DUST_SALT)
		);
	}
}
