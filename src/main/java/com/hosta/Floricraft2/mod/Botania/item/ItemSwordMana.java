package com.hosta.Floricraft2.mod.Botania.item;

import javax.annotation.Nonnull;

import com.hosta.Floricraft2.item.ItemBaseSword;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class ItemSwordMana extends ItemBaseSword implements IManaUsingItem {

	public static final int MANA_PER_DAMAGE = 60;

	public ItemSwordMana(String name, ToolMaterial material)
	{
		super(name, material);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, @Nonnull EntityLivingBase par3EntityLivingBase)
	{
		damage(par1ItemStack, 1, par3EntityLivingBase);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World world, IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity)
	{
		if(state.getBlockHardness(world, pos) != 0F)
		{
			damage(stack, 1, entity);
		}
		return true;
	}

	protected void damage(ItemStack stack, int damage, EntityLivingBase entity)
	{
		if(usesMana(stack))
		{
			ToolCommons.damageItem(stack, damage, entity, getManaPerDamage());
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5)
	{
		if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, getManaPerDamage() * 2, true))
		{
			stack.setItemDamage(stack.getItemDamage() - 1);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack)
	{
		return (par2ItemStack.getItem() == ModItems.manaResource && par2ItemStack.getItemDamage() == 0) || super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public boolean usesMana(ItemStack stack)
	{
		return true;
	}

	private int getManaPerDamage()
	{
		return MANA_PER_DAMAGE;
	}

}
