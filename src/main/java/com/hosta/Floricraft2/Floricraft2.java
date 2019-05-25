package com.hosta.Floricraft2;

import com.hosta.Floricraft2.config.Config;
import com.hosta.Floricraft2.module.ModuleFloricraft2;
import com.hosta.Floricraft2.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class Floricraft2 {

	@Mod.Instance(Reference.MOD_ID)
	public static Floricraft2		fc;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy		proxy;

	public static ModuleFloricraft2	BASE	= new ModuleFloricraft2();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.lord(event.getSuggestedConfigurationFile());
		BASE.preinit();
		proxy.registerEvents();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		BASE.Init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		BASE.postinit();
	}
}
