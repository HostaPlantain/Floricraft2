package com.hosta.Floricraft2.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBase extends Enchantment {

	public EnchantmentBase(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
		this.setName(name);
	}
}
