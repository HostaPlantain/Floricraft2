package com.hosta.Floricraft2.client.gui;

import com.hosta.Floricraft2.item.doll.ItemMessage;
import com.hosta.Floricraft2.item.doll.ItemMessage.Message;
import com.hosta.Floricraft2.module.ModuleDolls;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMessage extends GuiContainer {

	public GuiMessage(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		ItemStack main = this.mc.player.getHeldItemMainhand();
		ItemStack off = this.mc.player.getHeldItemOffhand();
		if (main.getItem() instanceof ItemMessage)
		{
			this.drawMessage(main);
		}
		else if (off.getItem() instanceof ItemMessage)
		{
			this.drawMessage(off);
		}
	}

	private void drawMessage(ItemStack stack)
	{
		Message message = ((ItemMessage) ModuleDolls.LETTER_RECEIVED).getMessage(stack);
		if (message != null)
		{
			this.fontRenderer.drawString(message.getMessage() + "\n\nby " + message.getAuthor(), 0, 0, 0);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		
	}

}
