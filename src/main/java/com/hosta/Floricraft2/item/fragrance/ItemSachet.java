package com.hosta.Floricraft2.item.fragrance;

import java.util.List;

import javax.annotation.Nullable;

import com.hosta.Floricraft2.item.ItemBasicTool;
import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSachet extends ItemBasicTool {

	public ItemSachet(String unlocalizedName)
	{
		super(unlocalizedName, 7200);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			int addedEffects = this.appendEffects(stack, player);
			if (addedEffects > 0)
			{
				addedEffects = 1 << (addedEffects - 1);
				this.damageItem(stack, addedEffects, player, itemSlot);
			}
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public int appendEffects(ItemStack stack, EntityPlayer player)
	{
		int i = 0;
		for (PotionEffect effect : Helper.getEffects(stack))
		{
			if (this.appendEffect(player, effect))
			{
				i += effect.getAmplifier() + 1;
			}
		}
		return i;
	}

	private boolean appendEffect(EntityPlayer player, PotionEffect effect)
	{
		boolean flag = !player.isPotionActive(effect.getPotion());
		if (!player.world.isRemote)
		{
			if (!flag)
			{
				PotionEffect active = player.getActivePotionEffect(effect.getPotion());
				flag = Helper.canMerge(effect, active);
			}
			if (flag)
			{
				player.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() + 10, effect.getAmplifier(), true, false));
			}
		}
		return flag;
	}

	protected void damageItem(ItemStack stack, int damage, EntityPlayer player, int itemSlot)
	{
		if (stack.getItemDamage() + damage <= stack.getMaxDamage())
		{
			stack.damageItem(damage, player);
		}
		else if (stack.getItemDamage() < stack.getMaxDamage())
		{
			stack.damageItem(stack.getMaxDamage() - stack.getItemDamage(), player);
		}
		else
		{
			onBreakSachet(stack, player, itemSlot);
		}
	}

	protected void onBreakSachet(ItemStack stack, EntityPlayer player, int itemSlot)
	{
		player.renderBrokenItemStack(stack);
		ItemStack sachetSac = getBrokenItem(stack);
		if (player.inventory.getStackInSlot(itemSlot) == stack)
		{
			player.inventory.setInventorySlotContents(itemSlot, sachetSac);
		}
	}

	public ItemStack damageItem(ItemStack stack, int damage)
	{
		if (stack.getItemDamage() + damage <= stack.getMaxDamage())
		{
			stack.setItemDamage(stack.getItemDamage() + damage);
		}
		else
		{
			stack = getBrokenItem(stack);
		}
		return stack;
	}

	protected ItemStack getBrokenItem(ItemStack stack)
	{
		ItemStack sachetSac = new ItemStack(ModuleFragrances.SACHET_SAC);
		sachetSac.setTagCompound(stack.getTagCompound());
		return sachetSac;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        PotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }
}
