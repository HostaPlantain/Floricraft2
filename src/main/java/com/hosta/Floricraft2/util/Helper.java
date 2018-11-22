package com.hosta.Floricraft2.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Helper {

	public static void spawnParticleOn(Entity entity, EnumParticleTypes particle)
	{
		AxisAlignedBB aabb = entity.getEntityBoundingBox();
		double x = MathHelper.rand(aabb.minX - 0.5D, aabb.maxX + 0.5D);
		double y = MathHelper.rand((aabb.maxY + aabb.minY) / 2.0D, aabb.maxY + 0.5D);
		double z = MathHelper.rand(aabb.minZ - 0.5D, aabb.maxZ + 0.5D);
		entity.world.spawnParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
	}
	
	public static Random getRand(IBlockAccess world)
	{
		return world instanceof World ? ((World)world).rand : new Random();
	}
}
