package com.hosta.Floricraft2.mod;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.mod.Baubles.ModuleBaubles;
import com.hosta.Floricraft2.mod.TiC.ModuleFloriconstract;
import com.hosta.Floricraft2.mod.thaumcraft.ModuleThaum;
import com.hosta.Floricraft2.module.Module;

import net.minecraftforge.fml.common.Loader;

public class ModedInit {

	public static boolean isTiCLoaded;
	//public static boolean isJEILoaded;
	public static boolean isBaublesLoaded;
	//public static boolean isBotaniaLoaded;
	public static boolean isThaumLoaded;
	
	private static final List<Module> MODDED_MODULE = new ArrayList<Module>();
	
	public static void preInit()
	{
		isTiCLoaded = Loader.isModLoaded("tconstruct");
		if (isTiCLoaded) {MODDED_MODULE.add(new ModuleFloriconstract());}
		
		isBaublesLoaded = Loader.isModLoaded("baubles");
		if (isBaublesLoaded) {MODDED_MODULE.add(new ModuleBaubles());}
		
		isThaumLoaded = Loader.isModLoaded("thaumcraft");
		if (isThaumLoaded) {MODDED_MODULE.add(new ModuleThaum());}
		
		MODDED_MODULE.forEach(module -> module.preInit());
	}
	
	public static void init()
	{
		MODDED_MODULE.forEach(module -> module.init());
	}
	
	public static void postInit()
	{
		MODDED_MODULE.forEach(module -> module.postInit());
	}
}
