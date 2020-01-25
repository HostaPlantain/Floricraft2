package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.util.Registries;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.brewing.BrewingRecipe;

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
		Registries.BLOCKS.register(entry);
	}

	public default void register(Item... entry)
	{
		Registries.ITEMS.register(entry);
	}

	public default void register(Potion... entry)
	{
		Registries.POTIONS.register(entry);
	}

	public default void register(Enchantment... entry)
	{
		Registries.ENCHANTMENTS.register(entry);
	}

	public default void register(IRecipe... entry)
	{
		Registries.RECIPES.register(entry);
	}

	public default void register(BrewingRecipe... entry)
	{
		Registries.RECIPES.register(entry);
	}
}
