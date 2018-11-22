package com.hosta.Floricraft2.block;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class BlockBasicFalling extends BlockFalling {

	public BlockBasicFalling(String name)
	{
		super(Material.SAND);
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT).setHarvestLevel("shovel", 0);
	}
}
