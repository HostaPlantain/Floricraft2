package com.hosta.Floricraft2.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
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

	public static List<PotionEffect> getEffects(ItemStack stack)
	{
		return PotionUtils.getEffectsFromStack(stack);
	}

	public static ItemStack setEffect(ItemStack stack, PotionEffect potion)
	{
		List<PotionEffect> list = new ArrayList<PotionEffect>();
		list.add(potion);
		return appendEffects(stack, list);
	}

	public static ItemStack addEffect(ItemStack stack, PotionEffect potion)
	{
		List<PotionEffect> list = getEffects(stack);
		return appendEffects(stack, mergeEffect(list, potion));
	}

	public static ItemStack appendEffects(ItemStack stack, List<PotionEffect> list)
	{
		return PotionUtils.appendEffects(stack, list);
	}

	public static List<PotionEffect> mergeEffect(List<PotionEffect> list, PotionEffect potion)
	{
		boolean hasEffect = false;
		for (PotionEffect potionOld : list)
		{
			if (potion.getPotion() == potionOld.getPotion())
			{
				if (canMerge(potion, potionOld))
				{
					list.remove(potionOld);
				}
				else
				{
					hasEffect = true;
				}
				break;
			}
		}
		if (!hasEffect)
		{
			list.add(potion);
		}
		return list;
	}

	public static boolean canMerge(PotionEffect EffectNew, PotionEffect EffectOld)
	{
		if (EffectNew.getAmplifier() > EffectOld.getAmplifier())
		{
			return true;
		}
		else if (EffectNew.getAmplifier() == EffectOld.getAmplifier())
		{
			return EffectNew.getDuration() > EffectOld.getDuration();
		}
		else
		{
			return false;
		}
	}

	public static void healBadEffect(EntityLivingBase entity, int tick)
	{
		Collection<PotionEffect> collection = entity.getActivePotionEffects();
		List<PotionEffect> list = new ArrayList<PotionEffect>();

		for (PotionEffect effect : collection)
		{
			if (effect.getPotion().isBadEffect() && effect.getDuration() > tick)
			{
				PotionEffect effectNew = new PotionEffect(effect.getPotion(), effect.getDuration() - tick, (effect.getAmplifier() != 0 && entity.world.rand.nextInt(20) == 0) ? effect.getAmplifier() - 1 : effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
				entity.removePotionEffect(effect.getPotion());
				list.add(effectNew);
			}
		}

		for (PotionEffect effectNew : list)
		{
			entity.addPotionEffect(effectNew);
		}
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

	public static <T> List<T> toList(T[] array)
	{
		List<T> list = new ArrayList<T>();
		for (T t : array)
		{
			list.add(t);
		}
		return list;
	}
}
