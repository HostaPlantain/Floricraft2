package com.hosta.Floricraft2.mod;

import com.hosta.Floricraft2.mod.TiC.TiCInit;

import net.minecraftforge.fml.common.Loader;

public class ModedInit {

	//public static boolean isBaublesLoaded;
	//public static boolean isBotaniaLoaded;
	//public static boolean isJEILoaded;
	//public static boolean isMantleLoaded;
	//public static boolean isRFLoaded;
	//public static boolean isTOPLoaded;
	public static boolean isTiCLoaded;
	
	public static void init()
	{
		//isBaublesLoaded = Loader.isModLoaded("baubles");
		//isBotaniaLoaded = Loader.isModLoaded("Botania");
		//isJEILoaded = Loader.isModLoaded("jei");
		//isMantleLoaded = Loader.isModLoaded("mantle");
		//isRFLoaded = Loader.isModLoaded("redstoneflux");
		//isTOPLoaded = Loader.isModLoaded("theoneprobe");
		
		isTiCLoaded = Loader.isModLoaded("tconstruct");
		if (isTiCLoaded)
		{
			TiCInit.init();
		}
	}
}
