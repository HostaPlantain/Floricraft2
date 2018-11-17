package com.hosta.Floricraft2.mod.Baubles;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.mod.Baubles.item.CharmSachet;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.module.ModuleRecipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleBaubles extends Module {

	//Amulet Sachet
	public static final Item CHARM_SACHET_FLOWER = new CharmSachet("charm_sachet_flower", ModuleOthers.POTION_FLORIC);
	public static final Item[] CHARM_SACHET_ANTIS = new Item[ModuleItems.SACHET_ANTIS.length];
	static
	{
		String sachetAnti = "charm_sachet_anti_";
		for (int i = 0; i < CHARM_SACHET_ANTIS.length; i++)
		{
			CHARM_SACHET_ANTIS[i] = new CharmSachet(sachetAnti + ModuleOthers.ANTI_MOBS[i], ModuleOthers.POTION_ANTIS[i]);
		}
	}
	
	private static final List<Item> ITEMS = new ArrayList<Item>();
	static
	{
		ITEMS.add(CHARM_SACHET_FLOWER);
		for (Item sachet : CHARM_SACHET_ANTIS)
		{
			ITEMS.add(sachet);
		}
	}

	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> event)
	{
		List<IRecipe> recipes = new ArrayList<IRecipe>();
		
		recipes.add(charmRecipe(CHARM_SACHET_FLOWER, ModuleItems.SACHET_FLOWER));
		for (int i = 0; i < CHARM_SACHET_ANTIS.length; i++)
		{
			recipes.add(charmRecipe(CHARM_SACHET_ANTIS[i], ModuleItems.SACHET_ANTIS[i]));
		}
		
		int count = 0;
		for (IRecipe recipe : recipes)
		{
			Module.registerRecipe(event.getRegistry(), recipe, recipe.getRecipeOutput().getUnlocalizedName().substring(5) + "_" + count++);
		}
	}
	
	private static IRecipe charmRecipe(Item charm, Item sachet)
	{
		return ModuleRecipes.shapedRecipe("charm_sachet", new ItemStack(charm, 1, 0), " t ", "t t", " s ", 't', ModuleItems.HEMP_TWINE, 's', new ItemStack(sachet, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		ITEMS.forEach(item -> Module.registerItem(event.getRegistry(), item));
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		ITEMS.forEach(item -> Module.registerItemRender(item));
	}
	
	@Subscribe
	public void postInit(FMLPostInitializationEvent event)
	{
		ITEMS.clear();
	}
}
