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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

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
				return "˜0";
			case BLUE:
				return "˜1";
			case GREEN:
				return "˜2";
			case CYAN:
				return "˜3";
			case RED:
				return "˜4";
			case PURPLE:
				return "˜5";
			case ORANGE:
				return "˜6";
			case BROWN:
				return "˜6";
			case SILVER:
				return "˜7";
			case GRAY:
				return "˜8";
			case LIGHT_BLUE:
				return "˜9";
			case LIME:
				return "˜a";
			case MAGENTA:
				return "˜b";
			// case : return "˜c";
			case PINK:
				return "˜d";
			case YELLOW:
				return "˜e";
			case WHITE:
				return "˜f";
			default:
				return "˜f";
		}
	}

	public static BlockPos getFloor(World world, BlockPos pos)
	{
		Chunk chunk = world.getChunkFromBlockCoords(pos);
        BlockPos blockpos;
        BlockPos blockposDown;

        for (blockpos = new BlockPos(pos); blockpos.getY() >= 0; blockpos = blockposDown)
        {
            blockposDown = blockpos.down();
            IBlockState state = chunk.getBlockState(blockposDown);

            if (state != Blocks.AIR.getDefaultState())
            {
                break;
            }
        }

        return blockpos;
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
