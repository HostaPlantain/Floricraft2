package com.hosta.Floricraft2.mod.Botania.client;

import com.hosta.Floricraft2.mod.Botania.entity.EntityProjectileIzanagi;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderProjectileIzanagi extends Render<EntityProjectileIzanagi> implements IRenderFactory<EntityProjectileIzanagi> {

	protected RenderProjectileIzanagi(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityProjectileIzanagi entity)
	{
		return null;
	}

	@Override
	public Render<? super EntityProjectileIzanagi> createRenderFor(RenderManager manager)
	{
		return null;
	}
}
