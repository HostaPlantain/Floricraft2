package com.hosta.Floricraft2.mod;

import com.hosta.Floricraft2.mod.Baubles.ModuleBaubles;
import com.hosta.Floricraft2.mod.Thaum.ModuleFloralia;
import com.hosta.Floricraft2.mod.TiC.ModuleFloriconstract;
import com.hosta.Floricraft2.module.IModule;
import com.hosta.Floricraft2.module.ModuleFloricraft2;

import net.minecraftforge.fml.common.Loader;

public class ModedInit {

	public static boolean isBaublesLoaded;
	//public static boolean isBotaniaLoaded;
	public static boolean isThaumLoaded;
	public static boolean isTiCLoaded;
	//public static boolean isJEILoaded;
	
	public static void load(ModuleFloricraft2 base)
	{
		isBaublesLoaded = Loader.isModLoaded("baubles");
		if (isBaublesLoaded) {base.register((IModule)new ModuleBaubles());}
		
		isThaumLoaded = Loader.isModLoaded("thaumcraft");
		if (isThaumLoaded) {base.register((IModule)new ModuleFloralia());}

		isTiCLoaded = Loader.isModLoaded("tconstruct");
		if (isTiCLoaded)
		{
			base.register((new ModuleFloriconstract()).registerPulse());
		}
	}
}
