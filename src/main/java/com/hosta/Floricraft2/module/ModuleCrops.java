package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.block.BlockBaseCrops;
import com.hosta.Floricraft2.item.ItemBase;
import com.hosta.Floricraft2.item.ItemBaseSeeds;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleCrops implements IModule, IModuleRecipe {

	// Crop
	public static final Block	CROP_HEMP	= new BlockBaseCrops("crop_hemp");
	public static final Item	SEED_HEMP	= new ItemBaseSeeds("seed_hemp", CROP_HEMP, Blocks.FARMLAND);

	// Hemp
	public static final Item	HEMP_YARN	= new ItemBase("hemp_yarn");
	public static final Item	HEMP_TWINE	= new ItemBase("hemp_twine");
	public static final Item	HEMP_SPOOL	= new ItemBase("hemp_spool");
	public static final Item	HEMP_CLOTH	= new ItemBase("hemp_cloth");

	@Override
	public void registerBlocks()
	{
		register(CROP_HEMP);
	}

	@Override
	public void registerItems()
	{
		register(SEED_HEMP, HEMP_YARN, HEMP_TWINE, HEMP_SPOOL, HEMP_CLOTH);
	}

	@Override
	public void registerOreDictionary()
	{
		// Crop
		OreDictionary.registerOre("seedHemp", SEED_HEMP);

		// Hemp
		OreDictionary.registerOre("fiberHemp", HEMP_YARN);
		OreDictionary.registerOre("fabricHemp", HEMP_CLOTH);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
				// Hemp
				shapelessRecipe(null, Items.STRING, "fiberHemp", "fiberHemp", "fiberHemp"),
				shapelessRecipe(null, HEMP_TWINE, "fiberHemp", "fiberHemp"),
				shapedRecipe(null, HEMP_SPOOL, " t ", "tst", " t ", 't', HEMP_TWINE, 's', Items.STICK),
				shapedRecipe(null, HEMP_CLOTH, "tt", "tt", "tt", 't', HEMP_TWINE)
		);
	}
}
