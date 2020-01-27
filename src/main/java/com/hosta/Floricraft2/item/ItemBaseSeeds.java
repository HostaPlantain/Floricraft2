package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ItemBaseSeeds extends ItemSeeds {

	public ItemBaseSeeds(String unlocalizedName, Block crops, Block soil)
	{
		super(crops, soil);
		this.setUnlocalizedName(unlocalizedName).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
		MinecraftForge.addGrassSeed(new ItemStack(this, 1, 0), 10);
	}
}
