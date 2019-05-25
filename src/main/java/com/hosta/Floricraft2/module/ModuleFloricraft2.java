package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.mod.ModedInit;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleFloricraft2 {

	private final List<IModule> MODULES = new ArrayList<IModule>();
	{
		this.register
		(
				(IModule) new ModuleCrops(), (IModule) new ModuleFlowering(), (IModule) new ModuleFragrances(),
				(IModule) new ModuleMaterials(), (IModule) new ModuleOthers(), (IModule) new ModuleTools()
		);
		ModedInit.load(this);
	}

	private boolean						readyForBlocks			= true;
	private boolean						readyForItems			= true;
	private boolean						readyForPotions			= true;
	private boolean						readyForEnchantments	= true;
	private boolean						readyForRecipes			= true;

	private final List<Block>			BLOCKS					= new ArrayList<Block>();
	private final List<Item>			ITEMS					= new ArrayList<Item>();
	private final List<Potion>			POTIONS					= new ArrayList<Potion>();
	private final List<Enchantment>		ENCHANTMENTS			= new ArrayList<Enchantment>();
	private final List<IRecipe>			RECIPES					= new ArrayList<IRecipe>();
	private final List<BrewingRecipe>	BREWING_RECIPES			= new ArrayList<BrewingRecipe>();

	public void register(IModule... module)
	{
		for (IModule m : module)
		{
			MODULES.add(m);
		}
	}

	public void register(Block... entry)
	{
		for (Block block : entry)
		{
			BLOCKS.add(block);
		}
	}

	public void register(Item... entry)
	{
		for (Item item : entry)
		{
			ITEMS.add(item);
		}
	}

	public void register(Potion... entry)
	{
		for (Potion potion : entry)
		{
			POTIONS.add(potion);
		}
	}

	public void register(Enchantment... entry)
	{
		for (Enchantment enchantment : entry)
		{
			ENCHANTMENTS.add(enchantment);
		}
	}

	public void register(IRecipe... entry)
	{
		for (IRecipe recipe : entry)
		{
			RECIPES.add(recipe);
		}
	}

	public void register(BrewingRecipe... entry)
	{
		for (BrewingRecipe recipe : entry)
		{
			BREWING_RECIPES.add(recipe);
		}
	}

	public void preinit()
	{
		MODULES.forEach(module -> module.preInit());
		MinecraftForge.EVENT_BUS.register(this);
	}

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

	public void Init()
	{
		MODULES.forEach(module -> module.init());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		RegisterHelper.registerItemRenders(ITEMS);
		RegisterHelper.registerItemBlockRenders(BLOCKS);
	}

	public void postinit()
	{
		MODULES.forEach(module -> module.postInit());
		BLOCKS.clear();
		ITEMS.clear();
		POTIONS.clear();
		ENCHANTMENTS.clear();
		RECIPES.clear();
	}
}
