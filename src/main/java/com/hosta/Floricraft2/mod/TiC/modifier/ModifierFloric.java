package com.hosta.Floricraft2.mod.TiC.modifier;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModifierFloric extends ModifierTrait {

	public ModifierFloric(String identifier, int color, int maxLevel, int countPerLevel)
	{
		super(identifier, color, maxLevel, countPerLevel);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if (!target.world.isRemote)
		{
			Vec3d pos = target.getPositionEyes(1.0F);
			Floricraft2.proxy.spawnParticleFloric(target.world, pos, 12, false, 8);
			
			NBTTagCompound tag = TinkerUtil.getModifierTag(tool, identifier);
			ModifierNBT data = ModifierNBT.readTag(tag);
	    	player.addPotionEffect(new PotionEffect(ModuleOthers.POTION_FLORIC, wasCritical ? 200 : 100, data.level - 1, false, false));
		}
	}
}
