package com.hosta.Floricraft2.client.gui;

import org.lwjgl.opengl.GL11;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.inventory.container.ContainerMessage;
import com.hosta.Floricraft2.item.doll.ItemMessage;
import com.hosta.Floricraft2.module.ModuleDolls;
import com.hosta.Floricraft2.util.Message;
import com.hosta.Floricraft2.util.RegisterHelper;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMessage extends GuiContainer {

	private static final ResourceLocation	BACK_LAYER		= RegisterHelper.getResourceLocation("textures/gui/gui_letter.png");
	private static final String				FAIL_MESSAGE	= "Connecting to the Server...";
	protected final String		AUTHOR;
	protected final String		LINES;

	public GuiMessage(EntityPlayer player)
	{
		super(new ContainerMessage());
		this.xSize = 150;
		this.ySize = 190;

		ItemStack main = player.getHeldItemMainhand();
		ItemStack off = player.getHeldItemOffhand();

		Message message;
		if (main.getItem() instanceof ItemMessage)
		{
			message = ((ItemMessage) ModuleDolls.LETTER_RECEIVED).getMessage(main);
		} else if (off.getItem() instanceof ItemMessage)
		{
			message = ((ItemMessage) ModuleDolls.LETTER_RECEIVED).getMessage(off);
		} else
		{
			message = Floricraft2.CONFIG.OFFLINE;
		}

		this.AUTHOR = message.getAuthor();
		this.LINES = message.getMessage();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.mc.fontRenderer.drawSplitString(this.LINES, 10, 15, 130, 0);
		int authorLength = this.fontRenderer.getStringWidth(this.AUTHOR);
		this.fontRenderer.drawString(ChatFormatting.ITALIC + this.AUTHOR, this.xSize - authorLength - 10, this.ySize - 15, 0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.mc.getTextureManager().bindTexture(BACK_LAYER);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}
