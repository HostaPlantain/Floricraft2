package com.hosta.Floricraft2.block;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	public BlockBase(String name, Material materialIn)
	{
		super(materialIn);
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
}
