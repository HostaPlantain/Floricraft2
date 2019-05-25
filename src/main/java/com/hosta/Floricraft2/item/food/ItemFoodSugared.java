package com.hosta.Floricraft2.item.food;

import com.hosta.Floricraft2.module.ModuleFlowering;
import com.hosta.Floricraft2.module.ModuleFragrances;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodSugared extends ItemBasicFoodMeta {

	public ItemFoodSugared(String unlocalizedName, String[] subName, int amount, float saturation)
	{
		super(unlocalizedName, subName, amount, saturation, false);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 10;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		if (stack.getItem() == ModuleFlowering.PETAL_SUGARED)
		{
			player.addPotionEffect(new PotionEffect(ModuleFragrances.POTION_FLORIC, 200, 0, false, false));
		}
		else if (stack.getItem() == ModuleFlowering.PETALS_SUGARED)
		{
			player.addPotionEffect(new PotionEffect(ModuleFragrances.POTION_FLORIC, 200, 3, false, false));
		}
	}
}
