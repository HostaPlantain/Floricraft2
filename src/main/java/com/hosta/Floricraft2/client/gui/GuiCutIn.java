package com.hosta.Floricraft2.client.gui;

import java.util.Map;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.hosta.Floricraft2.Reference;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCutIn extends Gui {

	protected int width		= 512;
	protected int height	= 512;

	private final ResourceLocation CUT_IN		= new ResourceLocation(Reference.MOD_ID, "textures/gui/cut_in.png");
	private final ResourceLocation EFFECTS		= new ResourceLocation(Reference.MOD_ID, "textures/gui/cut_in_ca.png");
	private final ResourceLocation SKIN_STEVE	= new ResourceLocation("textures/entity/steve.png");

	private ResourceLocation player;
	private int count = 0;

	public GuiCutIn(EntityPlayer player)
	{
		super();
		setSkin(player.getGameProfile());
	}

	public void setScaled(ScaledResolution scaled)
	{
		width = scaled.getScaledWidth();
		height = scaled.getScaledHeight();
	}

	private void setSkin(GameProfile profile)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		player = SKIN_STEVE;
		if (profile != null)
		{
			Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);
			if (map.containsKey(Type.SKIN))
			{
				player = minecraft.getSkinManager().loadSkin(map.get(Type.SKIN), Type.SKIN);
			}
			else
			{
				UUID uuid = profile.getId();
				player = DefaultPlayerSkin.getDefaultSkin(uuid);
			}
		}
	}

	private static final int FRAME_START	= 5;
	private static final int FRAME_END		= 35;
	private static final int FRAME_HIGHT	= 64;

	public boolean isEnded()
	{
		return count > FRAME_END;
	}

	public void draw()
	{
		GL11.glPushMatrix();
		drawRect(0, 0, width, height, 0x8f000000);
		if (count++ < FRAME_START)
		{
			int privateHight = (FRAME_HIGHT / FRAME_START) * (count);
			drawRect(0, (height / 2) - privateHight, width, (height / 2) + privateHight, 0xffffffff);
		}
		else if (count <= FRAME_END - FRAME_START)
		{
			drawCutIn();
		}
		else if (count <= FRAME_END)
		{
			int privateHight = (FRAME_HIGHT / FRAME_START) * (FRAME_END - count);
			drawRect(0, (height / 2) - privateHight, width, (height / 2) + privateHight, 0xffffffff);
		}
		GL11.glPopMatrix();
	}

	private void drawCutIn()
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		Minecraft.getMinecraft().getTextureManager().bindTexture(CUT_IN);
		drawTexturedModalRect(0, (height / 2) - 64, count * 4, 128, width, 128);

		float scale = 7.5f;
		GL11.glScalef(scale, scale, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(player);
		drawTexturedModalRect(width / 2 / scale - 16, height / 2 / scale - 8, 32, 42, 32, 16);
		GL11.glScalef(1 / scale, 1 / scale, 1.0f);

		Minecraft.getMinecraft().getTextureManager().bindTexture(CUT_IN);
		drawTexturedModalRect(0, height / 2 - 64, 0, 0, width, 128);

		Minecraft.getMinecraft().getTextureManager().bindTexture(EFFECTS);
		drawTexturedModalRect(width / 2 - 128, height / 2 - 96, 0, 0, 128, 128);
	}
}
