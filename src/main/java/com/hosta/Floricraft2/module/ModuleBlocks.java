package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.block.BlockStackDead;
import com.hosta.Floricraft2.block.BlockStackFlower;
import com.hosta.Floricraft2.item.IMetaName;
import com.hosta.Floricraft2.item.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleBlocks extends Module {

	//Stack Flower
	public static final Block[] STACK_FLOWER = new Block[ModuleItems.ALL.length];
	static 
	{
		for(int i = 0; i < STACK_FLOWER.length; i++)
		{
			STACK_FLOWER[i] = new BlockStackFlower("stack_" + ModuleItems.ALL[i], i);
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
	//public static final Block ORE_SALT;
	//public static final Block BLOCK_SALT;
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
		for(Block block : STACK_FLOWER)
		{
			BLOCKS.add(block);
		}
		BLOCKS.add(STACK_DEAD);
		BLOCKS.add(CROP_HEMP);
	}
	
	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		for(Block block : BLOCKS)
		{
			Module.registerBlock(event.getRegistry(), block);
		}
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		for(Block block : BLOCKS)
		{
			if (block instanceof IMetaName)
			{
				Module.registerItem(event.getRegistry(), new ItemBlockMeta(block));
			}
			else if (block instanceof BlockBasicCrops)
			{
				
			}
			else
			{
				Module.registerItem(event.getRegistry(), new ItemBlock(block));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		for(Block block : BLOCKS)
		{
			Module.registerItemRender(Item.getItemFromBlock(block));
		}
	}
	
	@Subscribe
	public void postInit(FMLPostInitializationEvent event)
	{
		BLOCKS.clear();
	}
}
