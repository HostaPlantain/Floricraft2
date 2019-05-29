package com.hosta.Floricraft2.mod.Baubles.item;

import com.hosta.Floricraft2.item.fragrance.ItemSachet;
import com.hosta.Floricraft2.module.ModuleFragrances;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class CharmSachet extends ItemSachet implements IBauble {

	public CharmSachet(String unlocalizedName, Potion potion)
	{
		super(unlocalizedName, potion);
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
			super.addEffect(player);
			this.damageItem(itemstack, player, this.getBaubleType(itemstack).getValidSlots()[0]);
		}
	}

	@Override
	protected void damageItem(ItemStack stack, EntityPlayer player, int itemSlot)
	{
		if (stack.getItemDamage() < this.getMaxDamage())
		{
			if (player.ticksExisted % 20 == 0)
			{
				stack.damageItem(1, player);
			}
		}
		else if (stack.getItemDamage() == this.getMaxDamage())
		{
			player.renderBrokenItemStack(stack);

			ItemStack sachetSac = new ItemStack(ModuleFragrances.SACHET_SAC);
			sachetSac.setTagCompound(stack.getTagCompound());

			if (BaublesApi.getBaubles(player).getStackInSlot(itemSlot) == stack)
			{
				BaublesApi.getBaubles(player).removeStackFromSlot(itemSlot);
				player.entityDropItem(sachetSac, 0.0F);
			}
			else
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setTag("empty", sachetSac.writeToNBT(new NBTTagCompound()));
				stack.setTagCompound(nbt);
			}
		}
	}
}
