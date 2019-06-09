package com.hosta.Floricraft2.inventory;

import com.hosta.Floricraft2.client.gui.GuiMessage;
import com.hosta.Floricraft2.inventory.container.ContainerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler {

	public static final int MESSAGE_RECEIVED = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case MESSAGE_RECEIVED:
				return new ContainerMessage();

			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case MESSAGE_RECEIVED:
				return new GuiMessage(player);

			default:
				return null;
		}
	}

}
