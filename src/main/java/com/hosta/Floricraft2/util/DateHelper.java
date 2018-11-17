package com.hosta.Floricraft2.util;

import java.util.Calendar;

import net.minecraft.world.World;

public class DateHelper {

	public static int getMonth(World world)	{return getMonth(world.getCurrentDate());}
	public static int getMonth(Calendar calendar)
	{
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getDate(World world)	{return getDate(world.getCurrentDate());}
	public static int getDate(Calendar calendar)
	{
		return calendar.get(Calendar.DATE);
	}
	
	public static boolean isNewYear(World world)
	{
		Calendar calendar = world.getCurrentDate();
		return getMonth(calendar) == 1 && getDate(calendar) == 1;
	}
	
	public static boolean isAprilFool(World world)
	{
		Calendar calendar = world.getCurrentDate();
		return getMonth(calendar) == 4 && getDate(calendar) == 1;
	}
	
	public static boolean isHalloween(World world)
	{
		Calendar calendar = world.getCurrentDate();
		return getMonth(calendar) == 10 && getDate(calendar) == 31;
	}
	
	public static boolean isChristmas(World world)
	{
		Calendar calendar = world.getCurrentDate();
		return getMonth(calendar) == 12 && (getDate(calendar) == 24 || getDate(calendar) == 25);
	}
}
