package com.hosta.Floricraft2.potion;

import java.util.List;

import com.hosta.Floricraft2.module.ModuleFragrances;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;

public class EffectAntiMob<T extends EntityCreature> extends EffectBase {

	private final Class<T>[] ANTI_CALSS;
	public final String ITEM;

	public EffectAntiMob(String name, int liquidColorIn, Class<T>[] list, String item)
	{
		super(name, liquidColorIn, false);
		ANTI_CALSS = list;
		ITEM = item;
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
			double rangeHori = 8;
			double rangeVert = 2;
			AxisAlignedBB antiArea = player.getEntityBoundingBox().expand(rangeHori, rangeVert, rangeHori).expand(-rangeHori, -rangeVert, -rangeHori);

			for (Class<T> c : ANTI_CALSS)
			{
				List<T> list = player.world.<T>getEntitiesWithinAABB(c, antiArea);
				for (T entity : list)
				{
					if (!entity.targetTasks.isControlFlagDisabled(8))
					{
						for (EntityAITasks.EntityAITaskEntry entry : entity.targetTasks.taskEntries)
						{
							if (entry.action instanceof EntityAINearestAttackableTarget && entry.action.getMutexBits() < 8)
							{
								entry.action.setMutexBits(entry.action.getMutexBits() | 8);
							}
						}
						entity.targetTasks.disableControlFlag(8);
					}
					entity.addPotionEffect(new PotionEffect(ModuleFragrances.POTION_NO_TARGET, 60, 0, false, false));
				}
			}
		}
	}

	public ItemStack getItemToCraft()
	{
		String[] splitCrux = ITEM.split(":");
		Item item = Item.getByNameOrId(splitCrux[0]+":"+splitCrux[1]);
		int meta = Integer.parseInt(splitCrux[2]);
		return new ItemStack(item, 1, meta);
	}
}
