package com.hosta.Floricraft2.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Helper {

	public static JsonElement getJsonFromURL(String path) throws IOException
	{
		URL url = new URL(path);
		InputStreamReader reader = new InputStreamReader(url.openStream());
		JsonParser parser = new JsonParser();
		return parser.parse(reader);
	}

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
		return world instanceof World ? ((World) world).rand : new Random();
	}

	public static String getCode(EnumDyeColor color)
	{
		switch (color)
		{
			case BLACK:
				return "Åò0";
			case BLUE:
				return "Åò1";
			case GREEN:
				return "Åò2";
			case CYAN:
				return "Åò3";
			case RED:
				return "Åò4";
			case PURPLE:
				return "Åò5";
			case ORANGE:
				return "Åò6";
			case BROWN:
				return "Åò6";
			case SILVER:
				return "Åò7";
			case GRAY:
				return "Åò8";
			case LIGHT_BLUE:
				return "Åò9";
			case LIME:
				return "Åòa";
			case MAGENTA:
				return "Åòb";
			// case : return "Åòc";
			case PINK:
				return "Åòd";
			case YELLOW:
				return "Åòe";
			case WHITE:
				return "Åòf";
			default:
				return "Åòf";
		}
	}
}
