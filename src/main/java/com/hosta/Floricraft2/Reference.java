package com.hosta.Floricraft2;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ModMetadata;

public class Reference {

	public static final String	MOD_ID				= "floricraft2";
	public static final String	MOD_ID_SHORT		= "fc.2";
	public static final String	MOD_NAME			= "Floricraft2";
	public static final String	VERSION				= "0.1.1";
	public static final String	DEPENDENCIES		= "after:baubles;after:thaumcraft;after:tconstruct;";

	public static final String	CLIENT_PROXY_CLASS	= "com.hosta.Floricraft2.proxy.ClientProxy";
	public static final String	SERVER_PROXY_CLASS	= "com.hosta.Floricraft2.proxy.CommonProxy";

	public static void setMeta(ModMetadata meta)
	{
		meta.version = VERSION;
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return path == null ? (ResourceLocation) null : new ResourceLocation(Reference.MOD_ID, path);
	}
}
