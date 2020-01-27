package com.hosta.Floricraft2.proxy;

import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.packet.PacketNBT;
import com.hosta.Floricraft2.packet.PacketNBTParticle;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void registerEvents() {}

	public void spawnParticleFloric(World world, Vec3d pos, int meta, boolean genByBlock, int number)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("x", pos.x);
		nbt.setDouble("y", pos.y);
		nbt.setDouble("z", pos.z);
		nbt.setInteger("meta", meta);
		nbt.setBoolean("genByBlock", genByBlock);
		nbt.setInteger("number", number);

		PacketNBT packet = new PacketNBTParticle(nbt);
		NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.x, pos.y, pos.z, 32);
		ModuleOthers.NETWORK_PARTICLE.sendToAllAround(packet, point);
	}

	public void setCutIn(EntityPlayer player) {}
}
