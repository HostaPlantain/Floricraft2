package com.hosta.Floricraft2.item.tool;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class ToolPruner extends ItemShears{
	
	public ToolPruner(String unlocalizedName)
	{
		this.setUnlocalizedName(unlocalizedName).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		int i = itemStack.getMetadata();
		return new ItemStack(this, 1, ++i);
	}
}