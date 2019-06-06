package com.hosta.Floricraft2.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;


public class ContainerMessage extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

}
