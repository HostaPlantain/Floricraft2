package com.hosta.Floricraft2.item.doll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.inventory.GuiHandler;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
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
		this.setMessage(worldIn, playerIn.getHeldItem(handIn));
		playerIn.openGui(Floricraft2.fc, GuiHandler.MESSAGE_RECEIVED, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);

		return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	private void setMessage(World worldIn, ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (!nbt.hasKey("message"))
		{
			Message message = MESSAGES.get(worldIn.rand.nextInt(MESSAGES.size()));
			nbt.setTag("message", this.getNBT(message));
			stack.setTagCompound(nbt);
		}
	}

	public Message getMessage(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (nbt.hasKey("message"))
		{
			NBTTagCompound message = nbt.getCompoundTag("message");
			return new Message(message.getString("author"), message.getString("text"));
		}
		return null;
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
			MESSAGES.add(new Message("Hosta_Plantain (Mod Author)", "Sorry! X(\nFailed to receive new Message..."));
		}
	}

	public class Message {

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

		public String getAuthor()
		{
			return this.AUTHOR;
		}

		public String getMessage()
		{
			return this.MESSAGE;
		}
	}
}
