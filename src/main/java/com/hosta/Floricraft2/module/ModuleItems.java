package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.item.ItemBasicMeta;
import com.hosta.Floricraft2.item.ItemBasicSeeds;
import com.hosta.Floricraft2.item.food.ItemFoodSugared;
import com.hosta.Floricraft2.item.tool.ToolPruner;
import com.hosta.Floricraft2.item.tool.ToolSachet;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleItems extends Module {

	public static final String[] FLOWERS = new String[]{"dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "sunflower", "lilac", "rose", "peony", "sakura"};
	public static final String[] COLORS = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
	public static final String[] ALL = new String[FLOWERS.length + COLORS.length];
	static
	{
		System.arraycopy(FLOWERS, 0, ALL, 0, FLOWERS.length);
		System.arraycopy(COLORS, 0, ALL, FLOWERS.length, COLORS.length);
	}
	public static final String[] ANTI_MOBS = new String[]{"zombie", "skeleton", "creeper", "spider", "ender"};
	
	//Material
	//Cut Flower
	public static final Item CUT_FLOWER = new ItemBasicMeta("cut_flower", ALL);
	//Petal
	public static final Item PETAL_RAW = new ItemBasicMeta("petal_raw", ALL);
	public static final Item PETAL_DRY = new ItemBasicMeta("petal_dry", ALL);
	public static final Item PETAL_SALTY = new ItemBasicMeta("petal_salty", ALL);
	public static final Item PETAL_SUGARED = new ItemFoodSugared("petal_sugared", ALL, 1, 0.5F);
	public static final Item PETALS_RAW = new ItemBasicMeta("petals_raw", ALL);
	public static final Item PETALS_DRY = new ItemBasicMeta("petals_dry", ALL);
	public static final Item PETALS_SALTY = new ItemBasicMeta("petals_salty", ALL);
	public static final Item PETALS_SUGARED = new ItemFoodSugared("petals_sugared", ALL, 2, 1.0F);
	//Ingot
	//public static final Item DUST_SALT;
	//Crop
	//Hemp
	public static final Item SEED_HEMP = new ItemBasicSeeds("seed_hemp", ModuleBlocks.CROP_HEMP, Blocks.FARMLAND);
	public static final Item HEMP_YARN = new ItemBasic("hemp_yarn");
	public static final Item HEMP_TWINE = new ItemBasic("hemp_twine");
	public static final Item HEMP_CLOTH = new ItemBasic("hemp_cloth");
	//Potion Item
	//Vial
	//public static final Item VIAL_EMPTY;
	//public static final Item VIAL_WATER;
	//public static final Item VIAL_FLOWER;
	//public static final Item VIAL_ANTIS;
	//Item Usable
	//public static final Item ITEM_BALLON;
	//Tool
	public static final Item PRUNER = new ToolPruner("pruner");
	//Basket
	//public static final Item BASKET_FLOWER;
	//public static final Item BASKET_LUNCH;
	//
	//public static final Item
	//public static final Item
	//Sachet
	public static final Item SACHET_SAC = new ItemBasic("sachet_sac");
	public static final Item SACHET_FLOWER = new ToolSachet("sachet_flower", (Potion)null);
	//public static final Item SACHET_ENDEARING;
	public static final Item[] SACHET_ANTIS = new Item[ANTI_MOBS.length];
	static
	{
		String sachetAnti = "sachet_anti_";
		for (int i = 0; i < SACHET_ANTIS.length; i++)
		{
			SACHET_ANTIS[i] = new ToolSachet(sachetAnti + ANTI_MOBS[i], ModuleOthers.POTION_ANTIS[i]);
		}
	}
	//Armor
	//Cloth Armor
	//public static final ArmorMaterial CLOTH;
	//public static final Item HELMET_CLOTH;
	//public static final Item CHESTPLATE_CLOTH;
	//public static final Item LEGGINGS_CLOTH;
	//public static final Item BOOTS_CLOTH;
	//public static final Item CHESTPLATE_APRON;
	//Weapon
	
	private static final List<Item> ITEMS = new ArrayList<Item>();
	static
	{
		ITEMS.add(CUT_FLOWER);
		ITEMS.add(PETAL_RAW);
		ITEMS.add(PETAL_DRY);
		ITEMS.add(PETAL_SALTY);
		ITEMS.add(PETAL_SUGARED);
		ITEMS.add(PETALS_RAW);
		ITEMS.add(PETALS_DRY);
		ITEMS.add(PETALS_SALTY);
		ITEMS.add(PETALS_SUGARED);
		ITEMS.add(SEED_HEMP);
		ITEMS.add(HEMP_YARN);
		ITEMS.add(HEMP_TWINE);
		ITEMS.add(HEMP_CLOTH);
		ITEMS.add(PRUNER);	
		ITEMS.add(SACHET_SAC);
		ITEMS.add(SACHET_FLOWER);
		for (Item sachet : SACHET_ANTIS)
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
