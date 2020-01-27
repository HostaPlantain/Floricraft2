package com.hosta.Floricraft2.block;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class BlockBaseFalling extends BlockFalling {

	public BlockBaseFalling(String name)
	{
		super(Material.SAND);
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT).setHarvestLevel("shovel", 0);
	}
}
