package com.hosta.Floricraft2.item.crafting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.hosta.Floricraft2.util.Helper;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;

public class RecipeNaming extends RecipeBasic {

	public RecipeNaming(ResourceLocation group)
	{
		super(group);
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return width * height >= 2;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn)
	{
		List<ItemStack> inputs = new ArrayList<ItemStack>();

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty())
			{
				inputs.add(itemstack);
			}
		}

		if (inputs.size() > 3)
		{
			return false;
		}

		return !this.getResult(inputs).isEmpty();
	}

	@Override
	@Nonnull
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		List<ItemStack> inputs = new ArrayList<ItemStack>();

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty())
			{
				inputs.add(itemstack);
			}
		}

		return this.getResult(inputs);
	}

	private ItemStack getResult(List<ItemStack> inputs)
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
