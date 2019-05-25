package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.item.tool.ToolPruner;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModuleTools implements IModule {

	public static final Item PRUNER = new ToolPruner("pruner");

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
