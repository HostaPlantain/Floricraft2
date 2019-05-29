package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.item.tool.ItemPruner;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModuleTools implements IModule {

	// Cloth Armor
	// public static final ArmorMaterial CLOTH;
	// public static final Item HELMET_CLOTH;
	// public static final Item CHESTPLATE_CLOTH;
	// public static final Item LEGGINGS_CLOTH;
	// public static final Item BOOTS_CLOTH;
	// public static final Item CHESTPLATE_APRON;
	
	// Weapon
	
	// Tool
	public static final Item PRUNER = new ItemPruner("pruner");

	@Override
	public void registerItems()
	{
		register(PRUNER);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
				shapedRecipe("pruner", PRUNER, "i ", "i ", " i", 'i', Items.IRON_INGOT),
				shapedRecipe("pruner", PRUNER, "i  ", " ii", 'i', Items.IRON_INGOT)
		);
	}
}
