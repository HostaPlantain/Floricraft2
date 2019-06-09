package com.hosta.Floricraft2.util;

import java.io.IOException;

import com.google.gson.JsonObject;

import net.minecraft.nbt.NBTTagCompound;

public class Message {

	private final String	AUTHOR;
	private final String	MESSAGE;

	public Message(JsonObject json) throws IOException
	{
		this(json.get("author").getAsString(), json.get("text").getAsString());
	}

	public Message(NBTTagCompound message)
	{
		this(message.getString("author"), message.getString("text"));
	}

	public Message(String author, String message)
	{
		AUTHOR = author;
		MESSAGE = message;
	}

	public String getAuthor()
	{
		return this.AUTHOR;
	}

	public String getMessage()
	{
		return this.MESSAGE;
	}
}
