package com.hosta.Floricraft2.recipe;

import java.util.List;

import com.hosta.Floricraft2.util.Helper;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.DyeUtils;

public class RecipeNaming extends RecipeBasic {

	public RecipeNaming(ResourceLocation group)
	{
		super(group);
		this.matchMax = 3;
	}

	@Override
	protected ItemStack getResult(List<ItemStack> inputs)
	{
		String name = null;
		EnumDyeColor color = EnumDyeColor.WHITE;
		ItemStack output = ItemStack.EMPTY;

		for (ItemStack itemIn : inputs)
		{
			if (itemIn.getItem() == Items.NAME_TAG & itemIn.hasDisplayName())
			{
				name = itemIn.getDisplayName().replaceAll("Åòo", "");
			}
			else if (DyeUtils.isDye(itemIn))
			{
				color = DyeUtils.colorFromStack(itemIn).get();
			}
			else if (!output.isEmpty())
			{
				return ItemStack.EMPTY;
			}
			else if (!itemIn.isEmpty())
			{
				output = itemIn.copy();
				output.setCount(1);
			}
		}

		if (!output.isEmpty() && name != null)
		{
			return output.setStackDisplayName(Helper.getCode(color) + name);
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public boolean isDynamic()
	{
		return true;
	}
}
