package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.block.BlockBasic;
import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.block.BlockBasicFalling;
import com.hosta.Floricraft2.block.BlockBasicOre;
import com.hosta.Floricraft2.block.BlockStackDead;
import com.hosta.Floricraft2.block.BlockStackFlower;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.item.ItemBasicMeta;
import com.hosta.Floricraft2.item.ItemBasicSeeds;
import com.hosta.Floricraft2.item.food.ItemFoodSugared;
import com.hosta.Floricraft2.item.tool.ToolPruner;
import com.hosta.Floricraft2.item.tool.ToolSachet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleItems extends Module {

	public static final String[] FLOWERS = new String[]{"dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "sunflower", "lilac", "rose", "peony", "sakura"};
	//public static final String[] COLORS = new String[]{"black", "red", "green", "brown", "blue", "purple", "cyan", "light_gray", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
	/*public static final String[] ALL = new String[FLOWERS.length + COLORS.length];
	static
	{
		System.arraycopy(FLOWERS, 0, ALL, 0, FLOWERS.length);
		System.arraycopy(COLORS, 0, ALL, FLOWERS.length, COLORS.length);
	}*/
	
	//Cut Flower
	public static final Item CUT_FLOWER = new ItemBasicMeta("cut_flower", FLOWERS);
	//Stack Flower
	public static final Block[] STACK_FLOWER = new Block[ModuleItems.FLOWERS.length];
	static 
	{
		for (int i = 0; i < STACK_FLOWER.length; i++)
		{
			STACK_FLOWER[i] = new BlockStackFlower("stack_" + ModuleItems.FLOWERS[i], i);
		}
	}
	public static final Block STACK_DEAD = new BlockStackDead("stack_dead");
	//Petal
	public static final Item PETAL_RAW = new ItemBasicMeta("petal_raw", FLOWERS);
	public static final Item PETAL_DRY = new ItemBasicMeta("petal_dry", FLOWERS);
	public static final Item PETAL_SALTY = new ItemBasicMeta("petal_salty", FLOWERS);
	public static final Item PETAL_SUGARED = new ItemFoodSugared("petal_sugared", FLOWERS, 1, 0.5F);
	public static final Item PETALS_RAW = new ItemBasicMeta("petals_raw", FLOWERS);
	public static final Item PETALS_DRY = new ItemBasicMeta("petals_dry", FLOWERS);
	public static final Item PETALS_SALTY = new ItemBasicMeta("petals_salty", FLOWERS);
	public static final Item PETALS_SUGARED = new ItemFoodSugared("petals_sugared", FLOWERS, 2, 1.0F);
	//Torch
	/*public static final Block[] TORCH_FLORIC = new Block[ModuleItems.ALL.length];
	static
	{
		for(int i = 0; i < TORCH_FLORIC.length; i++)
		{
			TORCH_FLORIC[i];
		}
	}*/
	//Ore
	//Ingot
	public static final Item INGOT_TWINKLE = new ItemBasic("ingot_twinkle");
	public static final Item NUGGET_TWINKLE = new ItemBasic("nugget_twinkle");
	public static final Block BLOCK_TWINKLE = new BlockBasic("block_twinkle", Material.IRON);
	//Salt
	public static final Item DUST_SALT = new ItemBasic("dust_salt");
	public static final Block ORE_SALT = new BlockBasicOre("ore_salt").setHardness(1.5F).setResistance(10.0F);
	public static final Block BLOCK_SALT = new BlockBasicFalling("block_salt").setHardness(0.5F);
	//Crop
	//Hemp
	public static final Block CROP_HEMP = new BlockBasicCrops("crop_hemp");
	public static final Item SEED_HEMP = new ItemBasicSeeds("seed_hemp", CROP_HEMP, Blocks.FARMLAND);
	public static final Item HEMP_YARN = new ItemBasic("hemp_yarn");
	public static final Item HEMP_TWINE = new ItemBasic("hemp_twine");
	//public static final Item HEMP_SPOOL = new ItemBasic("hemp_spool");
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
	//Shepherd
	//public static final Item SHEPHERD_CROOK;
	//public static final Item SHEPHERD_WHISTLE;
	//Sachet
	public static final Item SACHET_SAC = new ItemBasic("sachet_sac");
	public static final Item SACHET_FLOWER = new ToolSachet("sachet_flower", (Potion)null);
	//public static final Item SACHET_ENDEARING;
	public static final Item[] SACHET_ANTIS = new Item[ModuleOthers.ANTI_MOBS.length];
	static
	{
		String sachetAnti = "sachet_anti_";
		for (int i = 0; i < SACHET_ANTIS.length; i++)
		{
			SACHET_ANTIS[i] = new ToolSachet(sachetAnti + ModuleOthers.ANTI_MOBS[i], ModuleOthers.POTION_ANTIS[i]);
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
	
	//Block
	//Silage
	//public static final Block SILAGE;
	//Plant
	//Flower
	//public static final Block FLOWER;
	//Tree
	//public static final Block LEAVES;
	//public static final Block LOG;
	//public static final Block SAPLING;
	//TileEntity
	//Potpourri
	//public static final Block POTPOURRI;
	//Doll
	//public static final Block DOLL_IRON;
	//public static final Block DOLL_PLAYER;
	//Wether
	//public static final Block WEATHER_COCK;
	//Planter
	//public static final Block PLANTER_POT;
	//public static final Block PLANTER_BED;
	
	private static final List<Item> ITEMS = new ArrayList<Item>();
	private static final List<Block> BLOCKS = new ArrayList<Block>();
	
	static
	{
		ITEMS.add(CUT_FLOWER);
		for (Block block : STACK_FLOWER)
		{
			BLOCKS.add(block);
		}
		BLOCKS.add(STACK_DEAD);
		ITEMS.add(PETAL_RAW);
		ITEMS.add(PETAL_DRY);
		ITEMS.add(PETAL_SALTY);
		ITEMS.add(PETAL_SUGARED);
		ITEMS.add(PETALS_RAW);
		ITEMS.add(PETALS_DRY);
		ITEMS.add(PETALS_SALTY);
		ITEMS.add(PETALS_SUGARED);
		ITEMS.add(INGOT_TWINKLE);
		ITEMS.add(NUGGET_TWINKLE);
		BLOCKS.add(BLOCK_TWINKLE);
		ITEMS.add(DUST_SALT);
		BLOCKS.add(ORE_SALT);
		BLOCKS.add(BLOCK_SALT);
		BLOCKS.add(CROP_HEMP);
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
	public void registerBlocks(Register<Block> event)
	{
		registerBlocks(event.getRegistry(), BLOCKS);
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		registerItems(event.getRegistry(), ITEMS);
		registerItemBlocks(event.getRegistry(), BLOCKS);
		registerOreDictionary();
	}

	private void registerOreDictionary()
	{
		//Vanilla
		OreDictionary.registerOre("dustSugar", Items.SUGAR);
		//Petal
		for (int i = 0; i < FLOWERS.length; i++)
		{
			OreDictionary.registerOre("petalsDry", new ItemStack(PETALS_DRY, 1, i));
		}
		//Ingot
		OreDictionary.registerOre("ingotTwinkle", INGOT_TWINKLE);
		OreDictionary.registerOre("nuggetTwinkle", NUGGET_TWINKLE);
		OreDictionary.registerOre("blockTwinkle", BLOCK_TWINKLE);
		//Salt
		OreDictionary.registerOre("dustSalt", DUST_SALT);
		OreDictionary.registerOre("itemSalt", DUST_SALT);
		OreDictionary.registerOre("oreSalt", ORE_SALT);
		OreDictionary.registerOre("blockSalt", BLOCK_SALT);
		//Crop
		OreDictionary.registerOre("seedHemp", SEED_HEMP);
		//Hemp
		OreDictionary.registerOre("fiberHemp", HEMP_YARN);
		OreDictionary.registerOre("fabricHemp", HEMP_CLOTH);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		registerItemRenders(ITEMS);
		registerItemBlockRenders(BLOCKS);
	}
	
	@Override
	public void postInit()
	{
		ITEMS.clear();
		BLOCKS.clear();
	}
}
