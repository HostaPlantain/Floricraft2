package com.hosta.Floricraft2.item.doll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMessage extends ItemBasic {

	private static final List<Message> MESSAGES = new ArrayList<Message>();

	public ItemMessage(String name)
	{
		super(name);

		if (MESSAGES.isEmpty())
		{
			this.load();
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		Message message = getMessage(worldIn, playerIn.getHeldItem(handIn));
		
		System.out.println(message.getMessage() + " by " + message.getAuthor());

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private Message getMessage(World worldIn, ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (nbt.hasKey("message"))
		{
			return this.getMessage(nbt.getCompoundTag("message"));
		}

		Message message = MESSAGES.get(worldIn.rand.nextInt(MESSAGES.size()));
		nbt.setTag("message", this.getNBT(message));
		stack.setTagCompound(nbt);

		return message;
	}

	private Message getMessage(NBTTagCompound nbt)
	{
		return new Message(nbt.getString("author"), nbt.getString("text"));
	}

	private NBTTagCompound getNBT(Message message)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("author", message.getAuthor());
		nbt.setString("text", message.getMessage());
		return nbt;
	}

	private void load()
	{
		try
		{
			JsonArray array = Helper.getJsonFromURL("https://script.google.com/macros/s/AKfycbx0iNmuhGhA39f5FLF4ITYBen1ylNDtRbvtfrm0VA/exec").getAsJsonArray();
			for (JsonElement element : array)
			{
				MESSAGES.add(new Message(element.getAsJsonObject()));
			}
		}
		catch (Exception e)
		{
			MESSAGES.add(new Message("Hosta_Plantain (Mod Author)", "Sorry! X(\rFailed to receive new Message..."));
		}
	}

	private class Message {

		private final String AUTHOR;
		private final String MESSAGE;
		
		private Message(JsonObject json) throws IOException
		{
			this(json.get("author").getAsString(), json.get("text").getAsString());
		}

		private Message(String author, String message)
		{
			AUTHOR = author;
			MESSAGE = message;
		}

		private String getAuthor()
		{
			return this.AUTHOR;
		}

		private String getMessage()
		{
			return this.MESSAGE;
		}
	}
}
