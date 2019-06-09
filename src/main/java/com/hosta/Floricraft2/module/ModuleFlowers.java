package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.block.flower.BlockStackDead;
import com.hosta.Floricraft2.block.flower.BlockStackFlower;
import com.hosta.Floricraft2.item.ItemBasicMeta;
import com.hosta.Floricraft2.item.flower.ItemFoodSugared;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleFlowers implements IModule {

	public static final String[]	FLOWERS;
	static 
	{
		String[] defaultFlowers = new String[] { "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "sunflower", "lilac", "rose", "peony" };
		String[] addedFlowers = Floricraft2.CONFIG.getAddedFlowers();

		FLOWERS = new String[defaultFlowers.length + addedFlowers.length];
		System.arraycopy(defaultFlowers, 0, FLOWERS, 0, defaultFlowers.length);
		System.arraycopy(addedFlowers, 0, FLOWERS, defaultFlowers.length, addedFlowers.length);
	}

	// Cut Flower
	public static final Item	CUT_FLOWER		= new ItemBasicMeta("cut_flower", FLOWERS);
	
	// Stack Flower
	public static final Block[]	STACK_FLOWER	= new Block[FLOWERS.length];
	static
	{
		for (int i = 0; i < STACK_FLOWER.length; i++)
		{
			STACK_FLOWER[i] = new BlockStackFlower("stack_" + FLOWERS[i], i);
		}
	}
	public static final Block	STACK_DEAD		= new BlockStackDead("stack_dead");
	
	// Petal
	public static final Item	PETAL_RAW		= new ItemBasicMeta("petal_raw", FLOWERS);
	public static final Item	PETAL_DRY		= new ItemBasicMeta("petal_dry", FLOWERS);
	public static final Item	PETAL_SALTY		= new ItemBasicMeta("petal_salty", FLOWERS);
	public static final Item	PETAL_SUGARED	= new ItemFoodSugared("petal_sugared", FLOWERS, 1, 0.5F);
	public static final Item	PETALS_RAW		= new ItemBasicMeta("petals_raw", FLOWERS);
	public static final Item	PETALS_DRY		= new ItemBasicMeta("petals_dry", FLOWERS);
	public static final Item	PETALS_SALTY	= new ItemBasicMeta("petals_salty", FLOWERS);
	public static final Item	PETALS_SUGARED	= new ItemFoodSugared("petals_sugared", FLOWERS, 2, 1.0F);
	
	// Torch
	/*
	 * public static final Block[] TORCH_FLORIC = new Block[ModuleItems.ALL.length];
	 * static { for(int i = 0; i < TORCH_FLORIC.length; i++) { TORCH_FLORIC[i]; } }
	 */

	@Override
	public void registerBlocks()
	{
		register(STACK_FLOWER);
		register(STACK_DEAD);
	}

	@Override
	public void registerItems()
	{
		register(CUT_FLOWER, PETAL_RAW, PETAL_DRY, PETAL_SALTY, PETAL_SUGARED, PETALS_RAW, PETALS_DRY, PETALS_SALTY, PETALS_SUGARED);
	}

	@Override
	public void registerOreDictionary()
	{
		OreDictionary.registerOre("dustSugar", Items.SUGAR);
		OreDictionary.registerOre("petalsDry", new ItemStack(PETALS_DRY, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	public void registerRecipes()
	{
		ItemStack[] flowers = new ItemStack[FLOWERS.length];
		int flowerMeta = 0;
		flowers[flowerMeta++] = new ItemStack(Blocks.YELLOW_FLOWER, 1, 0);
		for (int i = 0; i < 9; i++)
		{
			flowers[flowerMeta++] = new ItemStack(Blocks.RED_FLOWER, 1, i);
		}
		flowers[flowerMeta++] = new ItemStack(Blocks.DOUBLE_PLANT, 1, 0);
		flowers[flowerMeta++] = new ItemStack(Blocks.DOUBLE_PLANT, 1, 1);
		flowers[flowerMeta++] = new ItemStack(Blocks.DOUBLE_PLANT, 1, 4);
		flowers[flowerMeta++] = new ItemStack(Blocks.DOUBLE_PLANT, 1, 5);
		
		for (int i = 0; i < flowerMeta; i++)
		{
			register
			(
					shapelessRecipe("cut_flower", new ItemStack(CUT_FLOWER, 1, i), flowers[i], new ItemStack(ModuleTools.PRUNER, 1, OreDictionary.WILDCARD_VALUE))
			);
		}

		for (int i = 0; i < FLOWERS.length; i++)
		{
			register
			(
					shapedRecipe("stack_flower", new ItemStack(STACK_FLOWER[i], 1, 0), "fff", "fff", "tft", 'f', new ItemStack(CUT_FLOWER, 1, i), 't', ModuleCrops.HEMP_TWINE),
					shapelessRecipe("petal_raw_from_stack", new ItemStack(PETAL_RAW, 2, i), new ItemStack(CUT_FLOWER, 1, i)),
					shapelessRecipe("petal_raw_from_petals", new ItemStack(PETAL_RAW, 9, i), new ItemStack(PETALS_RAW, 1, i)),
					compressRecipe("petals_raw", new ItemStack(PETALS_RAW, 1, i), new ItemStack(PETAL_RAW, 1, i), true),
					shapelessRecipe("petal_dry_from_stack", new ItemStack(PETAL_DRY, 4, i), new ItemStack(STACK_FLOWER[i], 1, 3)),
					shapelessRecipe("petal_dry_from_petals", new ItemStack(PETAL_DRY, 9, i), new ItemStack(PETALS_DRY, 1, i)),
					compressRecipe("petals_dry", new ItemStack(PETALS_DRY, 1, i), new ItemStack(PETAL_DRY, 1, i), true),
					shapelessRecipe("petal_salty_from_raw", new ItemStack(PETAL_SALTY, 1, i), new ItemStack(PETAL_RAW, 1, i), "dustSalt", "dustSalt"),
					shapelessRecipe("petal_salty_from_petals", new ItemStack(PETAL_SALTY, 9, i), new ItemStack(PETALS_SALTY, 1, i)),
					compressRecipe("petals_salty", new ItemStack(PETALS_SALTY, 1, i), new ItemStack(PETAL_SALTY, 1, i), true),
					shapelessRecipe("petal_sugared_from_raw", new ItemStack(PETAL_SUGARED, 1, i), new ItemStack(PETAL_RAW, 1, i), "dustSugar", "dustSugar"),
					shapelessRecipe("petal_sugared_from_petals", new ItemStack(PETAL_SUGARED, 9, i), new ItemStack(PETALS_SUGARED, 1, i)),
					compressRecipe("petals_sugared", new ItemStack(PETALS_SUGARED, 1, i), new ItemStack(PETAL_SUGARED, 1, i), true)
			);
		}
	}
}
