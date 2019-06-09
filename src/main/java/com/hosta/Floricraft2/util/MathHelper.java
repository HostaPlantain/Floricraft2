package com.hosta.Floricraft2.util;

import java.util.Random;

public class MathHelper {

	private static final Random rand = new Random();

	public static double rand(double min, double max)
	{
		return rand.nextDouble() * (max - min) + min;
	}
}
