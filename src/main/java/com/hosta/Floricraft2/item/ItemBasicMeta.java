package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBasicMeta extends ItemBasic implements IMetaName {

	private final String[] SUB_ID;

	public ItemBasicMeta(String name, String[] subName)
	{
		super(name);
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
