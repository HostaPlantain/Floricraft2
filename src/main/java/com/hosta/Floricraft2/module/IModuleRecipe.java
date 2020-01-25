package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.recipe.RecipeAppendEffect;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public interface IModuleRecipe {

	public default IRecipe shapedRecipe(String group, Item result, Object... recipe)
	{
		return shapedRecipe(group, new ItemStack(result), recipe);
	}

	public default IRecipe shapedRecipe(String group, Block result, Object... recipe)
	{
		return shapedRecipe(group, new ItemStack(result), recipe);
	}

	public default IRecipe shapedRecipe(String group, ItemStack result, Object... recipe)
	{
		return new ShapedOreRecipe(Reference.getResourceLocation(group), result, recipe);
	}

	public default IRecipe shapelessRecipe(String group, Item result, Object... recipe)
	{
		return shapelessRecipe(group, new ItemStack(result), recipe);
	}

	public default IRecipe shapelessRecipe(String group, Block result, Object... recipe)
	{
		return shapelessRecipe(group, new ItemStack(result), recipe);
	}

	public default IRecipe shapelessRecipe(String group, ItemStack result, Object... recipe)
	{
		return new ShapelessOreRecipe(Reference.getResourceLocation(group), result, recipe);
	}

	public default IRecipe effectRecipe(String group, Item result, Object... recipe)
	{
		return new RecipeAppendEffect(Reference.getResourceLocation(group), new ItemStack(result), recipe);
	}

	public default IRecipe compressRecipe(String group, Item result, ItemStack item, boolean isNine)
	{
		return compressRecipe(group, new ItemStack(result), item, isNine);
	}

	public default IRecipe compressRecipe(String group, Block result, ItemStack item, boolean isNine)
	{
		return compressRecipe(group, new ItemStack(result), item, isNine);
	}

	public default IRecipe compressRecipe(String group, ItemStack result, ItemStack item, boolean isNine)
	{
		Object[] recipe;
		if (isNine)
		{
			recipe = new Object[] { "iii", "iii", "iii", 'i', item };
		} else
		{
			recipe = new Object[] { "ii", "ii", 'i', item };
		}
		return shapedRecipe(group, result, recipe);
	}

	public default BrewingRecipe brewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output)
	{
		return new BrewingRecipe(input, ingredient, output);
	}
}
