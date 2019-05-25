package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.util.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public interface IModule {

	public default void preInit() {}

	public default void init() {}

	public default void postInit() {}

	public default void registerBlocks() {}

	public default void registerItems() {}

	public default void registerOreDictionary() {}

	public default void registerPotions() {}

	public default void registerEnchantments() {}

	public default void registerEntities() {}

	public default void registerRecipes() {}

	public default void register(Block... entry)
	{
		Floricraft2.BASE.register(entry);
	}

	public default void register(Item... entry)
	{
		Floricraft2.BASE.register(entry);
	}

	public default void register(Potion... entry)
	{
		Floricraft2.BASE.register(entry);
	}

	public default void register(Enchantment... entry)
	{
		Floricraft2.BASE.register(entry);
	}

	public default void register(IRecipe... entry)
	{
		Floricraft2.BASE.register(entry);
	}

	public default void register(BrewingRecipe... entry)
	{
		Floricraft2.BASE.register(entry);
	}

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
		return new ShapedOreRecipe(RegisterHelper.getResourceLocation(group), result, recipe);
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
		return new ShapelessOreRecipe(RegisterHelper.getResourceLocation(group), result, recipe);
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
		}
		else
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
