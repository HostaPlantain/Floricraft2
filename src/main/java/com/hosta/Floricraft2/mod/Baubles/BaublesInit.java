package com.hosta.Floricraft2.mod.Baubles;

import net.minecraftforge.common.MinecraftForge;

public class BaublesInit {

	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(new ModuleBaubles());
	}
}
