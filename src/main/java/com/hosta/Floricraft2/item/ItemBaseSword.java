package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.item.ItemSword;

public class ItemBaseSword extends ItemSword {

	public ItemBaseSword(String name, ToolMaterial material)
	{
		super(material);
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
}
