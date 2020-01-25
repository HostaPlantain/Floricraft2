package com.hosta.Floricraft2.mod.Baubles.item;

import com.hosta.Floricraft2.item.fragrance.ItemSachet;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CharmSachet extends ItemSachet implements IBauble {

	public CharmSachet(String unlocalizedName)
	{
		super(unlocalizedName);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.CHARM;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase playerIn)
	{
		if (playerIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) playerIn;
			int addedEffects = this.appendEffects(itemstack, player);
			if (addedEffects > 0)
			{
				addedEffects = 1 << (addedEffects - 1);
				this.damageItem(itemstack, addedEffects, player, this.getBaubleType(itemstack).getValidSlots()[0]);
			}
		}
	}

	@Override
	protected void onBreakSachet(ItemStack stack, EntityPlayer player, int itemSlot)
	{
		player.renderBrokenItemStack(stack);
		ItemStack sachetSac = getBrokenItem(stack);
		if (BaublesApi.getBaublesHandler(player).getStackInSlot(itemSlot) == stack)
		{
			BaublesApi.getBaublesHandler(player).setStackInSlot(itemSlot, ItemStack.EMPTY);
			player.entityDropItem(sachetSac, 0.0F);
		}
	}
}
