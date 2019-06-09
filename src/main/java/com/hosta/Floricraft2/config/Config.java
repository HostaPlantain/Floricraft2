package com.hosta.Floricraft2.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.module.ModulePlants;
import com.hosta.Floricraft2.util.Helper;
import com.hosta.Floricraft2.util.Message;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {

	private final String[]	ADDED_FLOWERS;
	private final boolean	IS_ONLINE;
	private final Message[]	MESSAGES;

	public static final Message	OFFLINE	= new Message("Hosta_Plantain (Mod Author)", "Sorry! X(\nFailed to receive new Message...");

	public Config()
	{
		File file = new File(Loader.instance().getConfigDir(), Reference.MOD_ID + ".cfg");
		Configuration config = new Configuration(file);

		try
		{
			config.load();
			ADDED_FLOWERS = config.getStringList("addedFlowers", "Plants", ModulePlants.FLORIC_FLOWERS, "Additional Flowers for Petal Crafting");
			IS_ONLINE = config.getBoolean("getMessage", "Dolls", true, "Get Messages from Online");
		}
		finally
		{
			config.save();
		}

		if (IS_ONLINE)
		{
			MESSAGES = loadMessages();
		}
		else
		{
			MESSAGES = new Message[] { OFFLINE };
		}
	}

	private Message[] loadMessages()
	{
		List<Message> list = new ArrayList<Message>();

		try
		{
			JsonArray json = Helper.getJsonFromURL("https://script.google.com/macros/s/AKfycbx0iNmuhGhA39f5FLF4ITYBen1ylNDtRbvtfrm0VA/exec").getAsJsonArray();
			for (JsonElement obj : json)
			{
				list.add(new Message(obj.getAsJsonObject()));
			}
		}
		catch (Exception e)
		{
			list.add(OFFLINE);
		}

		return list.toArray(new Message[] {});
	}

	public String[] getAddedFlowers()
	{
		return ADDED_FLOWERS;
	}

	public Message[] getMessages()
	{
		return MESSAGES;
	}

	public Message getMessage(Random rand)
	{
		return MESSAGES[rand.nextInt(MESSAGES.length)];
	}
}
