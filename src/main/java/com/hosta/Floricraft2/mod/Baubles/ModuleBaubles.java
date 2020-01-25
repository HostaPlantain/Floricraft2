package com.hosta.Floricraft2.mod.Baubles;

import com.hosta.Floricraft2.mod.Baubles.item.CharmSachet;
import com.hosta.Floricraft2.module.IModule;
import com.hosta.Floricraft2.module.IModuleRecipe;
import com.hosta.Floricraft2.module.ModuleCrops;
import com.hosta.Floricraft2.module.ModuleFragrances;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleBaubles implements IModule, IModuleRecipe {

	// Amulet Sachet
	public static final Item	CHARM_SACHET_FLOWER	= new CharmSachet("charm_sachet_flower");

	@Override
	public void registerItems()
	{
		register(CHARM_SACHET_FLOWER);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
				effectRecipe(null, CHARM_SACHET_FLOWER, " t ", "t t", " a ", 'a', new ItemStack(ModuleFragrances.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleCrops.HEMP_TWINE)
		);
	}
}
