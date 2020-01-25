package com.hosta.Floricraft2.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeAppendEffect extends ShapedOreRecipe {

	public RecipeAppendEffect(ResourceLocation group, ItemStack itemOut, Object... recipe)
	{
		super(group, itemOut, recipe);
	}

	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1)
	{
		List<PotionEffect> list = new ArrayList<PotionEffect>();
		for (int i = 0; i < var1.getSizeInventory(); i++)
		{
			ItemStack itemstack = var1.getStackInSlot(i);
			if (!itemstack.isEmpty())
			{
				for (PotionEffect potion : Helper.getEffects(itemstack))
				{
					if (output.getItem() == ModuleFragrances.SACHET_FLOWER)
					{
						if (potion.getDuration() > 1)
						{
							Helper.mergeEffect(list, new PotionEffect(potion.getPotion(), 405, potion.getAmplifier()));
						}
					}
					else
					{
						Helper.mergeEffect(list, potion);
					}
				}
			}
		}
		return Helper.appendEffects(this.output.copy(), list);
	}
}
