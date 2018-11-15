package com.hosta.Floricraft2.mod.TiC;

import slimeknights.tconstruct.TConstruct;

public class TiCInit {

	public static void init()
	{
		TConstruct.pulseManager.registerPulse(new ModuleFloriconstract());
	}
}
