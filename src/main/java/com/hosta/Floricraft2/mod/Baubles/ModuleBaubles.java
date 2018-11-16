package com.hosta.Floricraft2.mod.Baubles;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.mod.Baubles.item.CharmSachet;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBaubles extends Module {

	//Amulet Sachet
	public static final Item CHARM_SACHET_FLOWER = new CharmSachet("charm_sachet_flower", ModuleOthers.POTION_FLORIC);
	public static final Item[] CHARM_SACHET_ANTIS = new Item[ModuleItems.SACHET_ANTIS.length];
	static
	{
		String sachetAnti = "charm_sachet_anti_";
		for (int i = 0; i < CHARM_SACHET_ANTIS.length; i++)
		{
			CHARM_SACHET_ANTIS[i] = new CharmSachet(sachetAnti + ModuleItems.ANTI_MOBS[i], ModuleOthers.POTION_ANTIS[i]);
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
