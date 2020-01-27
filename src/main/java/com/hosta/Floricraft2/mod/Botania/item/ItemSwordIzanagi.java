package com.hosta.Floricraft2.mod.Botania.item;

import javax.annotation.Nonnull;

import com.google.common.collect.Multimap;
import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.entity.EntityBaseProjectile;
import com.hosta.Floricraft2.mod.Botania.entity.EntityProjectileIzanagi;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;

public class ItemSwordIzanagi extends ItemSwordMana {

	public ItemSwordIzanagi(String name)
	{
		super(name, BotaniaAPI.terrasteelToolMaterial);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		Floricraft2.proxy.setCutIn(playerIn);
		if (!worldIn.isRemote)
		{
			EntityBaseProjectile entity = new EntityProjectileIzanagi(worldIn);
			entity.shootByShooter(playerIn, 1.0f, -1.0f);
			worldIn.spawnEntity(entity);
		}
		damage(playerIn.getHeldItem(handIn), 100, playerIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.removeAll(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName());
			multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier("Weapon modifier", 1.0, 0));
		}
		return multimap;
	}
}
