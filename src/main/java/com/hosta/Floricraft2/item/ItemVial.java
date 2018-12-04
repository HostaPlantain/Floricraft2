package com.hosta.Floricraft2.item;

import com.hosta.Floricraft2.module.ModuleFragrances;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemVial extends ItemGlassBottle {

	public ItemVial(String name)
	{
		super();
		this.setUnlocalizedName(name).setCreativeTab(ModuleOthers.TAB_FLORICRAFT);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult trace = this.rayTrace(worldIn, playerIn, true);
		
		if (trace == null)
		{
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
		else if (trace.typeOfHit == RayTraceResult.Type.BLOCK)
		{
			BlockPos blockpos = trace.getBlockPos();
			if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER)
        	{
				if (!worldIn.isDaytime() && worldIn.getCurrentMoonPhaseFactor() >= 0.9f && blockpos == worldIn.getTopSolidOrLiquidBlock(blockpos))
				{
					for (EntityItem entity : worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(blockpos)))
					{
						if (entity.getItem().getItem() == Items.GHAST_TEAR)
						{
							ItemStack item = entity.getItem();
							item.splitStack(1);
							if (!item.isEmpty())
							{
								entity.setItem(item);
							}
							else
							{
								worldIn.removeEntity(entity);
							}
							
							worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.turnBottleIntoItem(itemstack, playerIn, new ItemStack(ModuleFragrances.VIAL_MOON)));
						}
					}
				}
				
				worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.turnBottleIntoItem(itemstack, playerIn, new ItemStack(ModuleFragrances.VIAL_WATER)));
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}
}
