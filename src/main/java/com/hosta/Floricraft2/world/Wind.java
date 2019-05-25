package com.hosta.Floricraft2.world;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Wind {

	private static final int	X			= 1700;
	private static final int	Z			= 1300;

	private static final int	TICK_X_MAX	= (int) Math.PI * 2 * X;
	private static final int	TICK_Z_MAX	= (int) Math.PI * 2 * Z;
	private static final int	TICL_MAX	= (int) Math.PI * 2 * X * Z;

	private static int			worldTick;
	private static double		windX;
	private static double		windY		= 0.0D;
	private static double		windZ;

	public static double[] getWind(World worldIn)
	{
		setWind(calcWorldTick(worldIn));
		return new double[] { windX, windY, windZ };
	}

	public static double getAngle(World worldIn)
	{
		setWind(calcWorldTick(worldIn));
		return getAngle();
	}

	private static double getAngle()
	{
		return Wind.windX <= 0 ? Math.acos(windZ / Math.sqrt((windZ * windZ) + (windX * windX))) : 6.2831853 - Math.acos(windZ / Math.sqrt((windZ * windZ) + (windX * windX)));
	}

	private static int calcWorldTick(World worldIn)
	{
		return (int) (worldIn.getTotalWorldTime() % TICL_MAX);
	}

	private static void setWind(int worldTick)
	{
		if (worldTick != Wind.worldTick)
		{
			Wind.worldTick = worldTick;
			setWindX(worldTick % TICK_X_MAX);
			setWindZ(worldTick % TICK_Z_MAX);
		}
	}

	private static void setWindX(float tickX)
	{
		Wind.windX = Wind.func(tickX / X) / 20;
	}

	private static void setWindZ(float tickZ)
	{
		Wind.windZ = Wind.func(tickZ / Z) / 20;
	}

	private static double func(float x)
	{
		return MathHelper.cos(x) + MathHelper.cos(x * 2) + MathHelper.cos(x * 5);
	}
}
