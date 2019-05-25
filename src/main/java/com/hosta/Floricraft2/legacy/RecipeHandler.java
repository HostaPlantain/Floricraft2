package com.hosta.Floricraft2.legacy;

import com.hosta.Floricraft2.Reference;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHandler {

	public static void registerCraftingRecipes()
	{

	}

	public static void registerFurnaceRecipes()
	{

	}

	public static void registerBrewingRecipes()
	{

	}

	private static void registerRecipes(String ID, IRecipe recipe)
	{
		Registerer.register(recipe, ID);
	}

	private static void registerShapedRecipes(String group, String ID, ItemStack resalt, Object[] recipe)
	{
		group = group == null ? "floricraft" : group;
		RecipeHandler.registerRecipes(ID, new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, group), resalt, recipe));
	}

	private static void registerShapelessRecipes(String group, String ID, ItemStack resalt, Object[] recipe)
	{
		group = group == null ? "floricraft" : group;
		RecipeHandler.registerRecipes(ID, new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, group), resalt, recipe));
	}

	private static void registerCompressRecipes(String group, String ID, ItemStack resalt, ItemStack item)
	{
		RecipeHandler.registerShapedRecipes(group, ID, resalt, new Object[] { "iii", "iii", "iii", 'i', item });
	}

	private static void registerEncloseRecipes(String group, String ID, ItemStack resalt, ItemStack center, ItemStack item)
	{
		RecipeHandler.registerShapedRecipes(group, ID, resalt, new Object[] { "iii", "ici", "iii", 'c', center, 'i', item });
	}
}
