package com.hosta.Floricraft2.block;

import java.util.Random;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.item.IMetaName;
import com.hosta.Floricraft2.module.ModuleFlowering;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStackFlower extends BlockStack implements IMetaName {

	public static final PropertyEnum<EnumStage>	DRYING	= PropertyEnum.create("drying", EnumStage.class);
	final int									FLOWER_TYPE;

	public BlockStackFlower(String name, int type)
	{
		super(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(DRYING, EnumStage.STAGE0));
		this.setTickRandomly(true);
		this.FLOWER_TYPE = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 3));
	}

	@Override
	public String[] getSubNames()
	{
		return new String[] { "raw", "raw", "raw", "dry" };
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(DRYING, EnumStage.getEnumFromMeta(meta));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return super.getStateFromMeta(meta).withProperty(DRYING, EnumStage.getEnumFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return super.getMetaFromState(state) + state.getValue(DRYING).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, DRYING });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(DRYING).getMeta() < 3 ? 0 : 3;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(state));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(state));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote && rand.nextBoolean())
		{
			int meta = getMetaFromState(state);
			if ((meta % 4) < 3)
			{
				if (this.canDry(worldIn, pos, state))
				{
					worldIn.setBlockState(pos, this.getStateFromMeta(++meta));
				} else
				{
					worldIn.setBlockState(pos, ModuleFlowering.STACK_DEAD.getDefaultState().withProperty(FACING, state.getValue(FACING)));
				}
			}
		}
	}

	private boolean canDry(World world, BlockPos pos, IBlockState state)
	{
		if (world.getLightFromNeighbors(pos.up()) == 15) { return false; }

		EnumFacing face = state.getValue(FACING);
		if (world.getBlockState(pos.offset(face.rotateY())).isOpaqueCube() || world.getBlockState(pos.offset(face.rotateYCCW())).isOpaqueCube())
		{
			return false;
		}

		return true;
	}

	public int getFlowerType()
	{
		return this.FLOWER_TYPE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(5) == 0 && getMetaFromState(stateIn) % 4 < 3)
		{
			EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
			double d0 = (double) pos.getX() + 0.5D + 0.38D * (double) enumfacing.getFrontOffsetX();
			double d1 = (double) pos.getY() + 0.5D - 0.22D;
			double d2 = (double) pos.getZ() + 0.5D + 0.38D * (double) enumfacing.getFrontOffsetZ();
			Vec3d position = new Vec3d(d0, d1, d2);

			Floricraft2.proxy.spawnParticleFloric(worldIn, position, this.getFlowerType(), true, 1);
		}
	}
}
