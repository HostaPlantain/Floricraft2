package com.hosta.Floricraft2.client.event;

import com.hosta.Floricraft2.client.gui.GuiCutIn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventCutIn {

	private static GuiCutIn guiCutIn = null;

	public static void setCutIn(EntityPlayer player)
	{
		guiCutIn = new GuiCutIn(player);
	}

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Post event)
	{
		if (event.getType() == ElementType.EXPERIENCE)
		{
			if (guiCutIn != null)
			{
				guiCutIn.setScaled(event.getResolution());
				guiCutIn.draw();
				if (guiCutIn.isEnded())
				{
					guiCutIn = null;
				}
			}
		}
	}
}
