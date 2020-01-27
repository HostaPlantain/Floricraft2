package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	public ItemBase(String name)
	{
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
}
