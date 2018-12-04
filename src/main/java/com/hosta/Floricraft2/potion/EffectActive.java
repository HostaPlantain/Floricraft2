package com.hosta.Floricraft2.potion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;

public class EffectActive extends EffectBasic {

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
				Collection<PotionEffect> collection = entityLivingBaseIn.getActivePotionEffects();
				List<PotionEffect> list = new ArrayList<PotionEffect>();
				
				for (PotionEffect effect : collection)
				{
					if(effect.getPotion().isBadEffect() && effect.getDuration() > 20)
					{
						PotionEffect effectNew = new PotionEffect(effect.getPotion(), effect.getDuration() - 20, (effect.getAmplifier() != 0 && entityLivingBaseIn.world.rand.nextInt(20) == 0) ? effect.getAmplifier() - 1 : effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
						entityLivingBaseIn.removePotionEffect(effect.getPotion());
						list.add(effectNew);
					}
				}
				
				for (PotionEffect effectNew : list)
				{
					entityLivingBaseIn.addPotionEffect(effectNew);
				}
				
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
			EntityLiving living = (EntityLiving)entityLivingBaseIn;
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
