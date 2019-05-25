package com.hosta.Floricraft2.config;

import java.io.File;

import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.module.ModulePlants;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {

	private static String[] addedFlowers;

	public static void lord()
	{
		File file = new File(Loader.instance().getConfigDir(), Reference.MOD_ID + ".cfg");
		Config.lord(file);
	}

	public static void lord(File file)
	{
		Configuration config = new Configuration(file);

		try
		{
			config.load();
			addedFlowers = config.getStringList("addedFlowers", "Flowering", ModulePlants.FLORIC_FLOWERS, "Additional Flowers for Petal Crafting");
		}
		finally
		{
			config.save();
		}
	}

	public static String[] getAddedFlowers()
	{
		return addedFlowers;
	}
}
