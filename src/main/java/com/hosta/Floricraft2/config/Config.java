package com.hosta.Floricraft2.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.util.Helper;
import com.hosta.Floricraft2.util.Message;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {

	private final Message[]			MESSAGES;
	private static final Message	OFFLINE	= new Message("Hosta_Plantain (Mod Author)", "Sorry! X(\nFailed to receive new Message...");

	private static final String[]	ANTI_POTION_STRINGS	= new String[]
			{
					"zombie;11393709;Minecraft:rotten_flesh:0;net.minecraft.entity.monster.EntityZombie;net.minecraft.entity.monster.EntityZombieVillager",
					"skeleton;11393709;Minecraft:bone:0;net.minecraft.entity.monster.EntitySkeleton;net.minecraft.entity.monster.EntityWitherSkeleton",
					"creeper;11393709;Minecraft:gunpowder:0;net.minecraft.entity.monster.EntityCreeper",
					"spider;11393709;Minecraft:spider_eye:0;net.minecraft.entity.monster.EntitySpider;net.minecraft.entity.monster.EntityCaveSpider",
					"ender;11393709;Minecraft:ender_pearl:0;net.minecraft.entity.monster.EntityEnderman"
			};
	private final String[]			ADDED_ANTI_POTIONS;

	private static final String[]	FLORIC_FLOWERS = new String[] { "sakura" };
	private final String[]			ADDED_FLOWERS;
	
	public Config()
	{
		File file = new File(Loader.instance().getConfigDir(), Reference.MOD_ID + ".cfg");
		Configuration config = new Configuration(file);

		boolean online;
		try
		{
			config.load();
			online = config.getBoolean("getMessage", "Dolls", true, "Get Messages from Online");
			ADDED_ANTI_POTIONS = config.getStringList("addedAntiPotions", "Fragrances", ANTI_POTION_STRINGS, "Additional Potion Effect to Avoid Creatures\nFormat: \"Category;Color;Item;Mob1;Mob2;Mob3...\"");
			ADDED_FLOWERS = config.getStringList("addedFlowers", "Plants", FLORIC_FLOWERS, "Additional Flowers for Petal Crafting");
		}
		finally
		{
			config.save();
		}
		MESSAGES = loadMessages(online);
	}

	private Message[] loadMessages(boolean online)
	{
		if (!online)
		{
			return new Message[] { OFFLINE };
		}
		
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

	public Message[] getMessages()
	{
		return MESSAGES;
	}

	public Message getMessage(Random rand)
	{
		return MESSAGES[rand.nextInt(MESSAGES.length)];
	}

	public String[] getAddedAntiPotions()
	{
		return ADDED_ANTI_POTIONS;
	}

	public String[] getAddedFlowers()
	{
		return ADDED_FLOWERS;
	}
}
