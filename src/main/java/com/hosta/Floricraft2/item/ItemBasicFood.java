package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.item.ItemFood;

public class ItemBasicFood extends ItemFood {

	public ItemBasicFood(String unlocalizedName, int amount, float saturation, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(unlocalizedName).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
}
