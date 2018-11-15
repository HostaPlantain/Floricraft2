package com.hosta.Floricraft2;

import com.hosta.Floricraft2.config.Config;
import com.hosta.Floricraft2.mod.ModedInit;
import com.hosta.Floricraft2.module.ModuleBlocks;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.module.ModuleRecipes;
import com.hosta.Floricraft2.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Floricraft2 {

	@Mod.Instance(Reference.MOD_ID)
    public static Floricraft2 instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.lord(event.getSuggestedConfigurationFile());
		
		MinecraftForge.EVENT_BUS.register(new ModuleBlocks());
		MinecraftForge.EVENT_BUS.register(new ModuleItems());
		MinecraftForge.EVENT_BUS.register(new ModuleOthers());
		MinecraftForge.EVENT_BUS.register(new ModuleRecipes());

		proxy.registerEvents();
		
		ModedInit.init();
	}
}
