package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleRecipes extends Module {

	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> event)
	{
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		
		ItemStack[] flowers = new ItemStack[ModuleItems.FLOWERS.length];
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
			//Cut Flower
			recipes.add(shapelessRecipe("cut_flower",				new ItemStack(ModuleItems.CUT_FLOWER, 1, i),		flowers[i], new ItemStack(ModuleItems.PRUNER, 1, OreDictionary.WILDCARD_VALUE)));
			//Petal
			recipes.add(shapedRecipe(	"stack_flower",				new ItemStack(ModuleItems.STACK_FLOWER[i], 1, 0),	"fff", "fff", "tft", 'f', new ItemStack(ModuleItems.CUT_FLOWER, 1, i), 't', ModuleItems.HEMP_TWINE));
			recipes.add(shapelessRecipe("petal_raw_from_stack",		new ItemStack(ModuleItems.PETAL_RAW, 2, i),			new ItemStack(ModuleItems.CUT_FLOWER, 1, i)));
			recipes.add(shapelessRecipe("petal_raw_from_petals",	new ItemStack(ModuleItems.PETAL_RAW, 9, i),			new ItemStack(ModuleItems.PETALS_RAW, 1, i)));
			recipes.add(compressRecipe(	"petals_raw",				new ItemStack(ModuleItems.PETALS_RAW, 1, i),		new ItemStack(ModuleItems.PETAL_RAW, 1, i),		true));
			recipes.add(shapelessRecipe("petal_dry_from_stack",		new ItemStack(ModuleItems.PETAL_DRY, 4, i),			new ItemStack(ModuleItems.STACK_FLOWER[i], 1, 3)));
			recipes.add(shapelessRecipe("petal_dry_from_petals",	new ItemStack(ModuleItems.PETAL_DRY, 9, i),			new ItemStack(ModuleItems.PETALS_DRY, 1, i)));
			recipes.add(compressRecipe(	"petals_dry",				new ItemStack(ModuleItems.PETALS_DRY, 1, i),		new ItemStack(ModuleItems.PETAL_DRY, 1, i),		true));
			recipes.add(shapelessRecipe("petal_salty_from_raw",		new ItemStack(ModuleItems.PETAL_SALTY, 1, i),		new ItemStack(ModuleItems.PETAL_RAW, 1, i), "dustSalt", "dustSalt"));
			recipes.add(shapelessRecipe("petal_salty_from_petals",	new ItemStack(ModuleItems.PETAL_SALTY, 9, i),		new ItemStack(ModuleItems.PETALS_SALTY, 1, i)));
			recipes.add(compressRecipe(	"petals_salty",				new ItemStack(ModuleItems.PETALS_SALTY, 1, i), 		new ItemStack(ModuleItems.PETAL_SALTY, 1, i),	true));
			recipes.add(shapelessRecipe("petal_sugared_from_raw",	new ItemStack(ModuleItems.PETAL_SUGARED, 1, i),		new ItemStack(ModuleItems.PETAL_RAW, 1, i), "dustSugar", "dustSugar"));
			recipes.add(shapelessRecipe("petal_sugared_from_petals",new ItemStack(ModuleItems.PETAL_SUGARED, 9, i),		new ItemStack(ModuleItems.PETALS_SUGARED, 1, i)));
			recipes.add(compressRecipe(	"petals_sugared",			new ItemStack(ModuleItems.PETALS_SUGARED, 1, i),	new ItemStack(ModuleItems.PETAL_SUGARED, 1, i),	true));
		}
		//Ingot
		recipes.add(compressRecipe(	null,			ModuleItems.INGOT_TWINKLE,							new ItemStack(ModuleItems.NUGGET_TWINKLE), true));
		recipes.add(shapelessRecipe(null,			new ItemStack(ModuleItems.INGOT_TWINKLE, 9, 0),		ModuleItems.BLOCK_TWINKLE));
		recipes.add(shapelessRecipe(null,			new ItemStack(ModuleItems.NUGGET_TWINKLE, 9, 0),	ModuleItems.INGOT_TWINKLE));
		recipes.add(compressRecipe(	null,			ModuleItems.BLOCK_TWINKLE,							new ItemStack(ModuleItems.INGOT_TWINKLE), true));
		//Salt
		recipes.add(shapelessRecipe(null,			new ItemStack(ModuleItems.DUST_SALT, 4, 0),			ModuleItems.BLOCK_SALT));
		recipes.add(shapedRecipe(	null,			ModuleItems.BLOCK_SALT,								"dd", "dd", 'd', ModuleItems.DUST_SALT));
		//Hemp
		recipes.add(shapelessRecipe(null,			Items.STRING,										"fiberHemp", "fiberHemp", "fiberHemp"));
		recipes.add(shapelessRecipe(null,			ModuleItems.HEMP_TWINE,								"fiberHemp", "fiberHemp"));
		recipes.add(shapedRecipe(	null,			ModuleItems.HEMP_CLOTH,								"tt", "tt", "tt", 't', ModuleItems.HEMP_TWINE));
		//Tool
		recipes.add(shapedRecipe(	"pruner",		ModuleItems.PRUNER,									"i ", "i ", " i", 'i', Items.IRON_INGOT));
		recipes.add(shapedRecipe(	"pruner",		ModuleItems.PRUNER,									"i  ", " ii", 'i', Items.IRON_INGOT));
		//Sachet
		recipes.add(shapedRecipe(	null,			ModuleItems.SACHET_SAC,								"ttt", "c c", " c ", 't', ModuleItems.HEMP_TWINE, 'c', ModuleItems.HEMP_CLOTH));
		recipes.add(shapedRecipe(	null,			new ItemStack(ModuleItems.SACHET_FLOWER, 1, 0),		"ppp", "ppp", "tst", 'p', "petalsDry", 's', ModuleItems.SACHET_SAC, 't', ModuleItems.HEMP_TWINE));
		int j = 0;
		recipes.add(shapedRecipe(	"sachet_anti",	new ItemStack(ModuleItems.SACHET_ANTIS[j++], 1, 0),	"ppp", "ppp", "tst", 'p', Items.ROTTEN_FLESH, 's', new ItemStack(ModuleItems.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleItems.HEMP_TWINE));
		recipes.add(shapedRecipe(	"sachet_anti",	new ItemStack(ModuleItems.SACHET_ANTIS[j++], 1, 0),	"ppp", "ppp", "tst", 'p', Items.BONE, 's', new ItemStack(ModuleItems.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleItems.HEMP_TWINE));
		recipes.add(shapedRecipe(	"sachet_anti",	new ItemStack(ModuleItems.SACHET_ANTIS[j++], 1, 0),	"ppp", "ppp", "tst", 'p', Items.GUNPOWDER, 's', new ItemStack(ModuleItems.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleItems.HEMP_TWINE));
		recipes.add(shapedRecipe(	"sachet_anti",	new ItemStack(ModuleItems.SACHET_ANTIS[j++], 1, 0),	"ppp", "ppp", "tst", 'p', Items.SPIDER_EYE, 's', new ItemStack(ModuleItems.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleItems.HEMP_TWINE));
		recipes.add(shapedRecipe(	"sachet_anti",	new ItemStack(ModuleItems.SACHET_ANTIS[j++], 1, 0),	"ppp", "ppp", "tst", 'p', Items.ENDER_PEARL, 's', new ItemStack(ModuleItems.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleItems.HEMP_TWINE));
		
		registerRecipes(event.getRegistry(), recipes);
	}

	public static IRecipe shapedRecipe(String group, Item result, Object... recipe)	{return shapedRecipe(group, new ItemStack(result), recipe);}
	public static IRecipe shapedRecipe(String group, Block result, Object... recipe){return shapedRecipe(group, new ItemStack(result), recipe);}	
	public static IRecipe shapedRecipe(String group, ItemStack result, Object... recipe)
	{
		return new ShapedOreRecipe(getResourceLocation(group), result, recipe);
	}

	public static IRecipe shapelessRecipe(String group, Item result, Object... recipe)	{return shapelessRecipe(group, new ItemStack(result), recipe);}
	public static IRecipe shapelessRecipe(String group, Block result, Object... recipe)	{return shapelessRecipe(group, new ItemStack(result), recipe);}
	public static IRecipe shapelessRecipe(String group, ItemStack result, Object... recipe)
	{
		return new ShapelessOreRecipe(getResourceLocation(group), result, recipe);
	}

	public static IRecipe compressRecipe(String group, Item result, ItemStack item, boolean isNine)	{return compressRecipe(group, new ItemStack(result), item, isNine);}
	public static IRecipe compressRecipe(String group, Block result, ItemStack item, boolean isNine)	{return compressRecipe(group, new ItemStack(result), item, isNine);}
	public static IRecipe compressRecipe(String group, ItemStack result, ItemStack item, boolean isNine)
	{
		Object[] recipe;
		if (isNine)
		{
			recipe = new Object[] {"iii", "iii", "iii", 'i', item};
		}
		else
		{
			recipe = new Object[] {"ii", "ii", 'i', item};			
		}
		return shapedRecipe(group, result, recipe);
	}
}
