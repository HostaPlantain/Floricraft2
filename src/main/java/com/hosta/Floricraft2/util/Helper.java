package com.hosta.Floricraft2.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;

public class Helper {

	public static void spawnParticleOn(Entity entity, EnumParticleTypes particle)
	{
		AxisAlignedBB aabb = entity.getEntityBoundingBox();
		double x = MathHelper.rand(aabb.minX - 0.5, aabb.maxX + 0.5);
		double y = MathHelper.rand((aabb.maxY - aabb.minY) / 2.0D, aabb.maxY + 0.5);;
		double z = MathHelper.rand(aabb.minZ - 0.5, aabb.maxZ + 0.5);;
		entity.world.spawnParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
	}
	
}