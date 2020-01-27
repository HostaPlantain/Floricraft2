package com.hosta.Floricraft2.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleBase extends Particle {

	public ParticleBase(World worldIn, double posXIn, double posYIn, double posZIn)
	{
		super(worldIn, posXIn, posYIn, posZIn);
	}

	public ParticleBase(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}
}
