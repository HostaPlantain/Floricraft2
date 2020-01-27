package com.hosta.Floricraft2.item.fragrance;

import java.util.List;

import javax.annotation.Nullable;

import com.hosta.Floricraft2.item.ItemBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVialEssence extends ItemBase {

	public ItemVialEssence(String name)
	{
		super(name);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }
}
