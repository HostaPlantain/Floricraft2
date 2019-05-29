package com.hosta.Floricraft2.block;

import javax.annotation.Nullable;

import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.tileentity.TileEntityBasic;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockEntityContainer extends BlockContainer {

	private final Class<? extends TileEntity> TE_CLASS;

	protected BlockEntityContainer(String name, Material materialIn, Class<? extends TileEntity> te)
	{
		super(materialIn);
		this.TE_CLASS = te;
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}

	public Class<? extends TileEntity> getTileEntityClass()
	{
		return this.TE_CLASS;
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	public abstract TileEntitySpecialRenderer<? extends TileEntity> getCustomRenderer();

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10))
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);

			if (tileEntity != null && tileEntity instanceof TileEntityBasic)
			{
				NBTTagCompound nbt = tileEntity.writeToNBT(new NBTTagCompound());
				NBTTagCompound nbtCopy = nbt.copy();

				nbt.merge((NBTTagCompound) stack.getTagCompound().getTag("BlockEntityTag"));
				nbt.setInteger("x", pos.getX());
				nbt.setInteger("y", pos.getY());
				nbt.setInteger("z", pos.getZ());

				if (!nbt.equals(nbtCopy))
				{
					tileEntity.readFromNBT(nbt);
					tileEntity.markDirty();
				}
			}
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
