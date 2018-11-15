package com.hosta.Floricraft2.item.tool;

import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ToolSachet extends ToolBasic {
	
	final Potion POTION;
	
	public ToolSachet(String unlocalizedName, Potion potion)
	{
		super(unlocalizedName, 7200);
		this.POTION = potion;
	}
	
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
    {
		if (entityIn instanceof EntityPlayer) 
		{
			EntityPlayer player = (EntityPlayer) entityIn ;
			
			this.addEffect(player, ModuleOthers.POTION_FLORIC, 400, 0);
			this.addEffect(player, POTION, 400, 0);
			this.damageItem(stack, player, itemSlot);
			
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
    }
	
	private void addEffect(EntityPlayer player, Potion potion, int duration, int amplifier)
	{
		if (!player.world.isRemote)
    	{
			if (!player.isPotionActive(potion) || player.getActivePotionEffect(potion).getDuration() < duration + 5 || player.getActivePotionEffect(potion).getAmplifier() < amplifier)
			{
				player.addPotionEffect(new PotionEffect(potion, duration + 15, amplifier, true, false));
			}
    	}
	}
	
	private void damageItem(ItemStack stack, EntityPlayer player, int itemSlot)
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

			ItemStack sachetSac = new ItemStack(ModuleItems.SACHET_SAC);
			sachetSac.setTagCompound(stack.getTagCompound());
			
			if (player.inventory.getStackInSlot(itemSlot) == stack)
			{
				player.inventory.setInventorySlotContents(itemSlot, sachetSac);
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
