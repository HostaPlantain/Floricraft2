package com.hosta.Floricraft2.mod.Botania.entity;

import com.hosta.Floricraft2.entity.EntityBaseProjectile;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityProjectileIzanagi extends EntityBaseProjectile {

	public EntityProjectileIzanagi(World worldIn)
	{
		super(worldIn);
		this.setNoGravity(true);
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (this.ticksExisted > 15)
		{
			BlockPos pos = this.getPosition();
			pos = Helper.getFloor(this.world, pos);
			this.world.addWeatherEffect(new EntityLightningBolt(this.world, pos.getX(), pos.getY(), pos.getZ(), false));
		}
		if (this.ticksExisted > 20)
		{
			this.setDead();
		}
	}
}
