package com.hosta.Floricraft2.proxy;

import com.hosta.Floricraft2.client.particle.ParticleBasic;
import com.hosta.Floricraft2.client.particle.ParticleFloric;
import com.hosta.Floricraft2.mod.TiC.ModuleFloriconstract;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy{

	private Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void registerEvents()
	{
		super.registerEvents();
	}
	
	@Override
	public void spawnParticleFloric(World world, Vec3d pos, int meta, boolean genByBlock, int number)
	{
		for (int i = 0; i < number; i++)
		{
			ParticleBasic particle = new ParticleFloric(world != null ? world : mc.world, pos, meta, genByBlock);
		    mc.effectRenderer.addEffect(particle);
		}
	}
	
	@Override
	public void registerTiCBook()
	{
		ModuleFloriconstract.registerBookPage();
	}
}
