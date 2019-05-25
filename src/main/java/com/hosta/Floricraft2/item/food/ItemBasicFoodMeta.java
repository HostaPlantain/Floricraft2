package com.hosta.Floricraft2.item.food;

import com.hosta.Floricraft2.item.IMetaName;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBasicFoodMeta extends ItemBasicFood implements IMetaName {

	private final String[] SUB_ID;

	public ItemBasicFoodMeta(String unlocalizedName, String[] subName, int amount, float saturation, boolean isWolfFood)
	{
		super(unlocalizedName, amount, saturation, isWolfFood);
		this.SUB_ID = subName;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (tab == ModuleOthers.TAB_FLORICRAFT)
		{
			for (int i = 0; i < this.countSubItems(); i++)
			{
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int i = stack.getMetadata() % this.countSubItems();
		return super.getUnlocalizedName() + "." + this.getSubName(i);
	}

	@Override
	public String[] getSubNames()
	{
		return SUB_ID;
	}
}
