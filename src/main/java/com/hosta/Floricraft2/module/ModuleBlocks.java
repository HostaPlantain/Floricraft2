package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.block.BlockBasicFalling;
import com.hosta.Floricraft2.block.BlockBasicOre;
import com.hosta.Floricraft2.block.BlockStackDead;
import com.hosta.Floricraft2.block.BlockStackFlower;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleBlocks extends Module {
	
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
	//Torch
	/*public static final Block[] TORCH_FLORIC = new Block[ModuleItems.ALL.length];
	static
	{
		for(int i = 0; i < TORCH_FLORIC.length; i++)
		{
			TORCH_FLORIC[i];
		}
	}*/
	//Silage
	//public static final Block SILAGE;
	//Ore
	//Salt
	public static final Block ORE_SALT = new BlockBasicOre("ore_salt").setHardness(1.5F).setResistance(10.0F);
	public static final Block BLOCK_SALT = new BlockBasicFalling("block_salt").setHardness(0.5F);
	//Plant
	//Flower
	//public static final Block FLOWER;
	//Tree
	//public static final Block LEAVES;
	//public static final Block LOG;
	//public static final Block SAPLING;
	//Crop
	public static final Block CROP_HEMP = new BlockBasicCrops("crop_hemp", ModuleItems.SEED_HEMP, ModuleItems.HEMP_TWINE);
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
	
	private static final List<Block> BLOCKS = new ArrayList<Block>();
	static
	{
		for (Block block : STACK_FLOWER)
		{
			BLOCKS.add(block);
		}
		BLOCKS.add(ORE_SALT);
		BLOCKS.add(BLOCK_SALT);
		BLOCKS.add(STACK_DEAD);
		BLOCKS.add(CROP_HEMP);
	}
	
	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		registerBlocks(event.getRegistry(), BLOCKS);
		registerOreDictionary();
	}
	
	private void registerOreDictionary()
	{
		//Salt
		OreDictionary.registerOre("oreSalt", ORE_SALT);
		OreDictionary.registerOre("block_salt", BLOCK_SALT);
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		registerItemBlocks(event.getRegistry(), BLOCKS);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		registerItemBlockRenders(BLOCKS);
	}
	
	@Override
	public void postInit()
	{
		BLOCKS.clear();
	}
}
