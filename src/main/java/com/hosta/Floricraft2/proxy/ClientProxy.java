package com.hosta.Floricraft2.proxy;

import com.hosta.Floricraft2.client.event.EventCutIn;
import com.hosta.Floricraft2.client.particle.ParticleBase;
import com.hosta.Floricraft2.client.particle.ParticleFloric;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerEvents()
	{
		super.registerEvents();
		MinecraftForge.EVENT_BUS.register(new EventCutIn());
	}

	@Override
	public void spawnParticleFloric(World world, Vec3d pos, int meta, boolean genByBlock, int number)
	{
		for (int i = 0; i < number; i++)
		{
			ParticleBase particle = new ParticleFloric(world != null ? world : mc.world, pos, meta, genByBlock);
			mc.effectRenderer.addEffect(particle);
		}
	}

	@Override
	public void setCutIn(EntityPlayer player)
	{
		EventCutIn.setCutIn(player);
	}
}
