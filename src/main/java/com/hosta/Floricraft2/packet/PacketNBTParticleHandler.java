package com.hosta.Floricraft2.packet;

import com.hosta.Floricraft2.Floricraft2;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketNBTParticleHandler implements IMessageHandler<PacketNBTParticle, PacketNBTParticle>{

	@SideOnly(Side.CLIENT)
	@Override
	public PacketNBTParticle onMessage(PacketNBTParticle message, MessageContext ctx)
	{
		NBTTagCompound nbt = message.nbt;
		Vec3d pos = new Vec3d(nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"));
		Floricraft2.proxy.spawnParticleFloric(null, pos, nbt.getInteger("meta"), nbt.getBoolean("genByBlock"), nbt.getInteger("number"));
		
		return null;
	}
}
