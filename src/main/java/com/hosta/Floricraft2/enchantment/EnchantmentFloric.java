package com.hosta.Floricraft2.enchantment;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;

public class EnchantmentFloric extends EnchantmentBasic {

	public EnchantmentFloric(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
	{
		super(name, rarityIn, typeIn, slots);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel)
	{
        return enchantmentLevel * 25;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
	{
        return this.getMinEnchantability(enchantmentLevel) + 50;
	}
	
	@Override
	public int getMaxLevel()
	{
		return 3;
	}
	
	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	{
		Vec3d pos = target.getPositionEyes(1.0F);
		Floricraft2.proxy.spawnParticleFloric(target.world, pos, 12, false, 4);
		
		user.addPotionEffect(new PotionEffect(ModuleOthers.POTION_FLORIC, 50, level - 1, false, false));
	}
}
