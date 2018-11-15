package com.hosta.Floricraft2.mod.TiC.client;

import org.lwjgl.opengl.GL11;

import com.hosta.Floricraft2.mod.TiC.ranged.EntityThrowingRose;
import com.hosta.Floricraft2.util.Matrix;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.renderer.RenderProjectileBase;

@SideOnly(Side.CLIENT)
public class RenderThrowingRose extends RenderProjectileBase<EntityThrowingRose> {

	public RenderThrowingRose(RenderManager renderManager)
	{
		super(renderManager);
	}
	
	@Override
	public void customRendering(EntityThrowingRose entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		float transX = -MathHelper.sin(Matrix.radian(entity.rotationYaw)) * MathHelper.cos(Matrix.radian(-entity.rotationPitch)) / 3;
		float transY = MathHelper.sin(Matrix.radian(-entity.rotationPitch)) / 3;
		float transZ = -MathHelper.cos(Matrix.radian(entity.rotationYaw)) * MathHelper.cos(Matrix.radian(-entity.rotationPitch)) / 3;
	    GL11.glTranslated(transX, transY, transZ);
	    
		GL11.glRotatef(entity.rotationYaw, 0f, 1f, 0f);
		GL11.glRotatef(-entity.rotationPitch, 1f, 0f, 0f);
		
		GL11.glRotatef(90f, 0f, 1f, 0f);
		GL11.glRotatef(-75f, 0f, 0f, 1f);
	}
}
