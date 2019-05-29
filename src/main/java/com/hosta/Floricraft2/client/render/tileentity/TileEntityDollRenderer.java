package com.hosta.Floricraft2.client.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class TileEntityDollRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> {

	protected static final double R = Math.PI / 8;

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial)
	{
		renderItem(te, x, y, z);
		super.render(te, x, y, z, partialTicks, destroyStage, partial);
	}

	protected abstract void renderItem(T entityDoll, double x, double y, double z);
}
