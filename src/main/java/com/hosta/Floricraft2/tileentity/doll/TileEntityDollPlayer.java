package com.hosta.Floricraft2.tileentity.doll;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

public class TileEntityDollPlayer extends TileEntityDoll {

	private GameProfile playerProfile;

	public void setDisplayedplayer(EntityPlayer player)
	{
		this.setDisplayedplayer(player.getGameProfile());
	}

	public void setDisplayedplayer(GameProfile player)
	{
		this.playerProfile = player;
	}

	public GameProfile getDisplayedplayer()
	{
		return this.playerProfile;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		if (this.getDisplayedplayer() != null)
		{
			NBTTagCompound nbtProfile = new NBTTagCompound();
			NBTUtil.writeGameProfile(nbtProfile, this.getDisplayedplayer());
			nbt.setTag("Owner", nbtProfile);
		}

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		if (nbt.hasKey("Owner"))
		{
			NBTTagCompound nbtProfile = nbt.getCompoundTag("Owner");
			this.setDisplayedplayer(NBTUtil.readGameProfileFromNBT(nbtProfile));
		}
	}
}
