package com.hosta.Floricraft2.potion;

import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;

public class EffectActive extends EffectBase {

	public EffectActive(String name, int liquidColorIn, boolean isBadEffect)
	{
		super(name, liquidColorIn, isBadEffect);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		if (this == ModuleFragrances.POTION_FLORIC && !entityLivingBaseIn.world.isRemote)
		{
			int i = 256 >> amplifier;
			if (i <= 0) {i = 1;}

			if (entityLivingBaseIn.ticksExisted % i == 0)
			{
				Helper.healBadEffect(entityLivingBaseIn, 20);

				if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
				{
					entityLivingBaseIn.heal(1.0f);
				}
			}
		}
		else if (this == ModuleFragrances.POTION_HILLSTEP)
		{
			entityLivingBaseIn.stepHeight = 1;
		}
		else if (this == ModuleFragrances.POTION_NO_TARGET && entityLivingBaseIn instanceof EntityLiving)
		{
			EntityLiving living = (EntityLiving) entityLivingBaseIn;
			if (living.getActivePotionEffect(this).getDuration() < 30)
			{
				if (!entityLivingBaseIn.world.isRemote)
				{
					living.targetTasks.enableControlFlag(8);
				}
				Helper.spawnParticleOn(living, EnumParticleTypes.VILLAGER_ANGRY);
			}
		}
	}
}
