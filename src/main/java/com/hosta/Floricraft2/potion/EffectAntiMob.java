package com.hosta.Floricraft2.potion;

import java.util.List;

import com.hosta.Floricraft2.module.ModuleFragrances;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;

public class EffectAntiMob<T extends EntityLiving> extends EffectBasic {

	private final Class<T>[] ANTI_CALSS;

	public EffectAntiMob(String name, int liquidColorIn, Class[] list)
	{
		super(name, liquidColorIn, false);
		ANTI_CALSS = list;
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
			AxisAlignedBB bb = player.getEntityBoundingBox().expand(8, 2, 8).expand(-8, -2, -8);

			for (Class<T> c : ANTI_CALSS)
			{
				List<T> list = player.world.<T>getEntitiesWithinAABB(c, bb);
				for (T entity : list)
				{
					for (EntityAITasks.EntityAITaskEntry entry : entity.targetTasks.taskEntries)
					{
						if (entry.action instanceof EntityAINearestAttackableTarget && entry.action.getMutexBits() < 8)
						{
							entry.action.setMutexBits(entry.action.getMutexBits() + 8);
						}
					}

					if (!entity.targetTasks.isControlFlagDisabled(8))
					{
						entity.targetTasks.disableControlFlag(8);
						entity.setAttackTarget((EntityLivingBase) null);
					}
					entity.addPotionEffect(new PotionEffect(ModuleFragrances.POTION_NO_TARGET, 60, 0, false, false));
				}
			}
		}
	}
}
