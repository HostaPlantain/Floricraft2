package com.hosta.Floricraft2.block.doll;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.hosta.Floricraft2.client.render.tileentity.TileEntityDollPlayerRenderer;
import com.hosta.Floricraft2.tileentity.doll.TileEntityDollPlayer;
import com.hosta.Floricraft2.util.Helper;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDollPlayer extends BlockDoll {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.875D, 0.75D);

	public BlockDollPlayer(String name)
	{
		super(name, TileEntityDollPlayer.class);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		TileEntityDollPlayer doll = (TileEntityDollPlayer) this.getTileEntityDoll(worldIn, pos);

		if (doll != null)
		{
			if (stack.hasDisplayName())
			{
				try
				{
					JsonObject json = Helper.getJsonFromURL("https://api.mojang.com/users/profiles/minecraft/" + stack.getDisplayName()).getAsJsonObject();
					String id = json.get("id").getAsString();
					id = id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20);
					UUID uuid = UUID.fromString(id);
					String name = json.get("name").getAsString();
					doll.setDisplayedplayer(new GameProfile(uuid, name));
				}
				catch (Exception e)
				{
					if (placer instanceof EntityPlayer)
					{
						doll.setDisplayedplayer((EntityPlayer) placer);
					}
				}
			}
			else if (placer instanceof EntityPlayer)
			{
				doll.setDisplayedplayer((EntityPlayer) placer);
			}
			doll.markDirty();
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDollPlayer();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TileEntitySpecialRenderer<? extends TileEntity> getCustomRenderer()
	{
		return new TileEntityDollPlayerRenderer();
	}
}
