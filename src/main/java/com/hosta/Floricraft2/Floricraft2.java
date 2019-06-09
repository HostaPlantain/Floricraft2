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
import com.hosta.Floricraft2.util.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
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

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Floricraft2 {

	@Mod.Instance(Reference.MOD_ID)
	public static Floricraft2	fc;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy	proxy;

	// Module
	private static boolean				readyForModules	= true;
	public static final Config 			CONFIG			= new Config();
	private static final List<IModule>	MODULES			= new ArrayList<IModule>();
	{
		this.register
		(
				(IModule) new ModuleCrops(),
				(IModule) new ModuleDolls(),
				(IModule) new ModuleFarm(),
				(IModule) new ModuleFlowers(),
				(IModule) new ModuleFragrances(),
				(IModule) new ModuleMaterials(),
				(IModule) new ModuleOthers(),
				(IModule) new ModulePlants(),
				(IModule) new ModuleTools()
		);
		ModedInit.load();
	}

	public static void register(IModule... entry)
	{
		if (readyForModules)
		{
			for (IModule module : entry)
			{
				MODULES.add(module);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
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

		BLOCKS.clear();
		ITEMS.clear();
		POTIONS.clear();
		ENCHANTMENTS.clear();
		RECIPES.clear();
	}

	// -----Registering System----- //

	private static boolean						readyForBlocks			= true;
	private static boolean						readyForItems			= true;
	private static boolean						readyForPotions			= true;
	private static boolean						readyForEnchantments	= true;
	private static boolean						readyForRecipes			= true;

	private static final List<Block>			BLOCKS					= new ArrayList<Block>();
	private static final List<Item>				ITEMS					= new ArrayList<Item>();
	private static final List<Potion>			POTIONS					= new ArrayList<Potion>();
	private static final List<Enchantment>		ENCHANTMENTS			= new ArrayList<Enchantment>();
	private static final List<IRecipe>			RECIPES					= new ArrayList<IRecipe>();
	private static final List<BrewingRecipe>	BREWING_RECIPES			= new ArrayList<BrewingRecipe>();

	public static void register(Block... entry)
	{
		if (readyForBlocks)
		{
			for (Block block : entry)
			{
				BLOCKS.add(block);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	public static void register(Item... entry)
	{
		if (readyForItems)
		{
			for (Item item : entry)
			{
				ITEMS.add(item);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	public static void register(Potion... entry)
	{
		if (readyForPotions)
		{
			for (Potion potion : entry)
			{
				POTIONS.add(potion);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	public static void register(Enchantment... entry)
	{
		if (readyForEnchantments)
		{
			for (Enchantment enchantment : entry)
			{
				ENCHANTMENTS.add(enchantment);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	public static void register(IRecipe... entry)
	{
		if (readyForRecipes)
		{
			for (IRecipe recipe : entry)
			{
				RECIPES.add(recipe);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	public static void register(BrewingRecipe... entry)
	{
		if (readyForRecipes)
		{
			for (BrewingRecipe recipe : entry)
			{
				BREWING_RECIPES.add(recipe);
			}
		}
		else
		{
			RegisterHelper.warn(entry);
		}
	}

	// -----Registering Events------//

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		MODULES.forEach(module -> module.registerBlocks());
		RegisterHelper.registerBlocks(event.getRegistry(), BLOCKS);
		readyForBlocks = false;
	}

	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		MODULES.forEach(module -> module.registerItems());
		RegisterHelper.registerItems(event.getRegistry(), ITEMS);
		RegisterHelper.registerItemBlocks(event.getRegistry(), BLOCKS);
		MODULES.forEach(module -> module.registerOreDictionary());
		readyForItems = false;
	}

	@SubscribeEvent
	public void registerPotions(Register<Potion> event)
	{
		MODULES.forEach(module -> module.registerPotions());
		RegisterHelper.registerPotions(event.getRegistry(), POTIONS);
		readyForPotions = false;
	}

	@SubscribeEvent
	public void registerEnchantments(Register<Enchantment> event)
	{
		MODULES.forEach(module -> module.registerEnchantments());
		RegisterHelper.registerEnchantments(event.getRegistry(), ENCHANTMENTS);
		readyForEnchantments = false;
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
		RegisterHelper.registerRecipes(event.getRegistry(), RECIPES);
		RegisterHelper.registerBrewingRecipe(BREWING_RECIPES);
		readyForRecipes = false;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		RegisterHelper.registerItemRenders(ITEMS);
		RegisterHelper.registerItemBlockRenders(BLOCKS);
	}
}
