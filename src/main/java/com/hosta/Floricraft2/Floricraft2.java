package com.hosta.Floricraft2;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.config.Config;
import com.hosta.Floricraft2.mod.ModedInit;
import com.hosta.Floricraft2.module.IModule;
import com.hosta.Floricraft2.module.ModuleCrops;
import com.hosta.Floricraft2.module.ModuleDolls;
import com.hosta.Floricraft2.module.ModuleFarm;
import com.hosta.Floricraft2.module.ModuleFlowers;
import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.module.ModuleMaterials;
import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.module.ModulePlants;
import com.hosta.Floricraft2.module.ModuleTools;
import com.hosta.Floricraft2.proxy.CommonProxy;
import com.hosta.Floricraft2.util.Helper;
import com.hosta.Floricraft2.util.Registries;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class Floricraft2 {

	@Mod.Instance(Reference.MOD_ID)
	public static Floricraft2			fc;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy			proxy;

	// Module
	private static boolean				readyForModules	= true;
	public static final Config			CONFIG			= new Config();
	private static final List<IModule>	MODULES			= new ArrayList<IModule>();
	static
	{
		Floricraft2.register(new ModuleCrops());
		Floricraft2.register(new ModuleDolls());
		Floricraft2.register(new ModuleFarm());
		Floricraft2.register(new ModuleFlowers());
		Floricraft2.register(new ModuleFragrances());
		Floricraft2.register(new ModuleMaterials());
		Floricraft2.register(new ModuleOthers());
		Floricraft2.register(new ModulePlants());
		Floricraft2.register(new ModuleTools());
		ModedInit.load();
	}

	public static void register(IModule... entry)
	{
		if (readyForModules)
		{
			MODULES.addAll(Helper.toList(entry));
		}
		else
		{
			// Log.warn("Too late! Registering has been failed!", entry);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Reference.setMeta(event.getModMetadata());
		readyForModules = false;
		MODULES.forEach(module -> module.preInit());
		MinecraftForge.EVENT_BUS.register(this);
		proxy.registerEvents();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MODULES.forEach(module -> module.init());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MODULES.forEach(module -> module.postInit());
	}

	// -----Registering Events------//

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		MODULES.forEach(module -> module.registerBlocks());
		Registries.BLOCKS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		MODULES.forEach(module -> module.registerItems());
		Registries.ITEMS.registerFinal(event.getRegistry());
		Registries.BLOCKS.registerItems(event.getRegistry());
		MODULES.forEach(module -> module.registerOreDictionary());
	}

	@SubscribeEvent
	public void registerPotions(Register<Potion> event)
	{
		MODULES.forEach(module -> module.registerPotions());
		Registries.POTIONS.registerFinal(event.getRegistry());
	}

	@SubscribeEvent
	public void registerEnchantments(Register<Enchantment> event)
	{
		MODULES.forEach(module -> module.registerEnchantments());
		Registries.ENCHANTMENTS.registerFinal(event.getRegistry());
	}

	/** WIP */
	@SubscribeEvent
	public void registerEntities(Register<EntityEntry> event)
	{
		MODULES.forEach(module -> module.registerEntities());
	}

	@SubscribeEvent
	public void registerRecipe(Register<IRecipe> event)
	{
		MODULES.forEach(module -> module.registerRecipes());
		Registries.RECIPES.registerFinal(event.getRegistry());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		Registries.ITEMS.registerRenders();
		Registries.BLOCKS.registerRenders();
	}
}
