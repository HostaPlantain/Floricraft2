package com.hosta.Floricraft2.recipe;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public abstract class RecipeBasic extends Impl<IRecipe> implements IRecipe {

	@Nonnull
	protected ItemStack					output	= ItemStack.EMPTY;
	protected final ResourceLocation	GROUP;

	public RecipeBasic(ResourceLocation group)
	{
		super();
		this.GROUP = group;
	}

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
