package com.hosta.Floricraft2;

import com.hosta.Floricraft2.config.Config;
import com.hosta.Floricraft2.mod.ModedInit;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleBlocks;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.module.ModuleRecipes;
import com.hosta.Floricraft2.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Floricraft2 {

	@Mod.Instance(Reference.MOD_ID)
    public static Floricraft2 instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	private static final Module[] MODULES = new Module[] {new ModuleBlocks(), new ModuleItems(), new ModuleOthers(), new ModuleRecipes()};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.lord(event.getSuggestedConfigurationFile());
		
		for (Module module : MODULES)
		{
			module.preInit();
		}
		
		proxy.registerEvents();
		
		ModedInit.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (Module module : MODULES)
		{
			module.init();
		}
		
		ModedInit.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		for (Module module : MODULES)
		{
			module.postInit();
		}
		
		ModedInit.postInit();
	}
}
