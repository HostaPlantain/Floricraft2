package com.hosta.Floricraft2.item.doll;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.inventory.GuiHandler;
import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.util.Message;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMessage extends ItemBasic {

	public ItemMessage(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		this.setMessage(worldIn, playerIn.getHeldItem(handIn));
		playerIn.openGui(Floricraft2.fc, GuiHandler.MESSAGE_RECEIVED, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	private void setMessage(World worldIn, ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (!nbt.hasKey("message"))
		{
			Message message = Floricraft2.CONFIG.getMessage(worldIn.rand);
			nbt.setTag("message", this.getNBT(message));
			stack.setTagCompound(nbt);
		}
	}

	public Message getMessage(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (nbt.hasKey("message"))
		{
			return new Message(nbt.getCompoundTag("message"));
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

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound() != null ? stack.getTagCompound() : new NBTTagCompound();
		if (nbt.hasKey("message"))
		{
			String author = nbt.getCompoundTag("message").getString("author");
			String fromBefor = I18n.format("custom.from.before");
			String fromAfter = I18n.format("custom.from.after");
			return super.getItemStackDisplayName(stack) + fromBefor + author + fromAfter;
		}
		return super.getItemStackDisplayName(stack);
	}
}
