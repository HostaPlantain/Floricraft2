package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleRecipes extends Module {
		
	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> event)
	{
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		
		for (int i = 0; i < ModuleItems.ALL.length; i++)
		{
			recipes.add(ModuleRecipes.compressRecipe("petals_raw", new ItemStack(ModuleItems.PETALS_RAW, 1, i), new ItemStack(ModuleItems.PETAL_RAW, 1, i), 9));
			recipes.add(ModuleRecipes.unCompressRecipe("petal_raw_from_petals", new ItemStack(ModuleItems.PETAL_RAW, 9, i), new ItemStack(ModuleItems.PETALS_RAW, 1, i)));
			recipes.add(ModuleRecipes.compressRecipe("petals_dry", new ItemStack(ModuleItems.PETALS_DRY, 1, i), new ItemStack(ModuleItems.PETAL_DRY, 1, i), 9));
			recipes.add(ModuleRecipes.unCompressRecipe("petal_dry_from_petals", new ItemStack(ModuleItems.PETAL_DRY, 9, i), new ItemStack(ModuleItems.PETALS_DRY, 1, i)));
			recipes.add(ModuleRecipes.compressRecipe("petals_salty", new ItemStack(ModuleItems.PETALS_SALTY, 1, i), new ItemStack(ModuleItems.PETAL_SALTY, 1, i), 9));
			recipes.add(ModuleRecipes.unCompressRecipe("petal_salty_from_petals", new ItemStack(ModuleItems.PETAL_SALTY, 9, i), new ItemStack(ModuleItems.PETALS_SALTY, 1, i)));
			recipes.add(ModuleRecipes.compressRecipe("petals_sugared", new ItemStack(ModuleItems.PETALS_SUGARED, 1, i), new ItemStack(ModuleItems.PETAL_SUGARED, 1, i), 9));
			recipes.add(ModuleRecipes.unCompressRecipe("petal_sugared_from_petals", new ItemStack(ModuleItems.PETAL_SUGARED, 9, i), new ItemStack(ModuleItems.PETALS_SUGARED, 1, i)));
		}
		
		int count = 0;
		for (IRecipe recipe : recipes)
		{
			Module.registerRecipe(event.getRegistry(), recipe, "recipe_" + count++);
		}
	}
	
	public static IRecipe compressRecipe(ItemStack result, ItemStack item, int amount)
	{
		return ModuleRecipes.compressRecipe((ResourceLocation)null, result, item, amount);
	}
	
	public static IRecipe compressRecipe(String group, ItemStack result, ItemStack item, int amount)
	{
		return ModuleRecipes.compressRecipe(new ResourceLocation(Reference.MOD_ID, group), result, item, amount);
	}
	
	public static IRecipe compressRecipe(ResourceLocation group, ItemStack result, ItemStack item, int amount)
	{
		Object[] recipe = new ItemStack[amount];
		for (int i = 0; i < amount; i++)
		{
			recipe[i] = item;
		}
		return new ShapelessOreRecipe(group, result, recipe);
	}

	public static IRecipe unCompressRecipe(ItemStack result, ItemStack item)
	{
		return ModuleRecipes.unCompressRecipe((ResourceLocation)null, result, item);
	}
	
	public static IRecipe unCompressRecipe(String group, ItemStack result, ItemStack item)
	{
		return ModuleRecipes.unCompressRecipe(new ResourceLocation(Reference.MOD_ID, group), result, item);
	}
	
	public static IRecipe unCompressRecipe(ResourceLocation group, ItemStack result, ItemStack item)
	{
		return new ShapelessOreRecipe(group, result, item);
	}
}
