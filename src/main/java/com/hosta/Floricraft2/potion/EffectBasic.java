package com.hosta.Floricraft2.potion;

import com.hosta.Floricraft2.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class EffectBasic extends Potion {

	public static final ResourceLocation icon = new ResourceLocation(Reference.MOD_ID, "textures/gui/effects.png");

	public EffectBasic(String name, int liquidColorIn, boolean isBadEffect)
	{
		super(isBadEffect, liquidColorIn);
		this.setPotionName(name);
		if (!isBadEffect)
		{
			this.setBeneficial();
		}
	}

	@Override
	public Potion setIconIndex(int x, int y)
	{
		super.setIconIndex(x, y);
		return this;
	}

	@Override
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(icon);
		return super.getStatusIconIndex();
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return false;
	}
}
