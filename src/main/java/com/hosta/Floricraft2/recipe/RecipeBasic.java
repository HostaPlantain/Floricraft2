package com.hosta.Floricraft2.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public abstract class RecipeBasic extends Impl<IRecipe> implements IRecipe {

	@Nonnull
	protected ItemStack					output	= ItemStack.EMPTY;
	protected final ResourceLocation	GROUP;
	protected int 	matchMin = 2;
	protected int 	matchMax = 2;

	public RecipeBasic(ResourceLocation group)
	{
		super();
		this.GROUP = group;
	}

	@Override
	public boolean canFit(int width, int height)
	{
		return width * height >= matchMin;
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

		if (inputs.size() > matchMax)
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

	protected abstract ItemStack getResult(List<ItemStack> inputs);

	@Override
	public ItemStack getRecipeOutput()
	{
		return this.output;
	}

	@Override
	@Nonnull
	public String getGroup()
	{
		return this.GROUP == null ? "" : this.GROUP.toString();
	}
}
