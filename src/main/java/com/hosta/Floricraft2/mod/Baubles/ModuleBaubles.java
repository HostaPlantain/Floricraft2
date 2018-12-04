package com.hosta.Floricraft2.mod.Baubles;

import com.hosta.Floricraft2.mod.Baubles.item.CharmSachet;
import com.hosta.Floricraft2.module.IModule;
import com.hosta.Floricraft2.module.ModuleCrops;
import com.hosta.Floricraft2.module.ModuleFragrances;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleBaubles implements IModule {

	//Amulet Sachet
	public static final Item CHARM_SACHET_FLOWER = new CharmSachet("charm_sachet_flower", ModuleFragrances.POTION_FLORIC);
	public static final Item[] CHARM_SACHET_ANTIS = new Item[ModuleFragrances.SACHET_ANTIS.length];
	static
	{
		String sachetAnti = "charm_sachet_anti_";
		for (int i = 0; i < CHARM_SACHET_ANTIS.length; i++)
		{
			CHARM_SACHET_ANTIS[i] = new CharmSachet(sachetAnti + ModuleFragrances.ANTI_MOBS[i], ModuleFragrances.POTION_ANTIS[i]);
		}
	}
	
	@Override
	public void registerItems()
	{
		register(CHARM_SACHET_FLOWER);
		register(CHARM_SACHET_ANTIS);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
			shapedRecipe("charm_sachet", new ItemStack(CHARM_SACHET_FLOWER, 1, 0), " t ", "t t", " s ", 't', ModuleCrops.HEMP_TWINE, 's', new ItemStack(ModuleFragrances.SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE)),
			shapelessRecipe(null, new ItemStack(CHARM_SACHET_FLOWER, 1, 0), new ItemStack(CHARM_SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), "petalsDry")
		);
		
		for (int i = 0; i < CHARM_SACHET_ANTIS.length; i++)
		{
			register
			(
				shapedRecipe("charm_sachet", new ItemStack(CHARM_SACHET_ANTIS[i], 1, 0), " t ", "t t", " s ", 't', ModuleCrops.HEMP_TWINE, 's', new ItemStack(ModuleFragrances.SACHET_ANTIS[i], 1, OreDictionary.WILDCARD_VALUE)),
				shapelessRecipe("charm_sachet", new ItemStack(CHARM_SACHET_ANTIS[i], 1, 0), new ItemStack(CHARM_SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModuleFragrances.VIAL_ANTIS, 1, i)),
				shapelessRecipe(null, new ItemStack(CHARM_SACHET_ANTIS[i], 1, 0), new ItemStack(CHARM_SACHET_ANTIS[i], 1, OreDictionary.WILDCARD_VALUE), "petalsDry")
			);
		}	
	}
}
