package com.hosta.Floricraft2.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
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
	
	public static String getCode(EnumDyeColor color)
	{
		switch (color)
		{
			case BLACK:		return "˜0";
			case BLUE:		return "˜1";
			case GREEN:		return "˜2";
			case CYAN:		return "˜3";
			case RED:		return "˜4";
			case PURPLE:	return "˜5";
			case ORANGE:	return "˜6";
			case BROWN:		return "˜6";
			case SILVER:	return "˜7";
			case GRAY:		return "˜8";
			case LIGHT_BLUE:return "˜9";
			case LIME:		return "˜a";
			case MAGENTA:	return "˜b";
			//case :		return "˜c";
			case PINK:		return "˜d";
			case YELLOW:	return "˜e";
			case WHITE:		return "˜f";
			default:		return "˜f";
		}
	}
}
