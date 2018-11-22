package com.hosta.Floricraft2.mod.TiC.ranged;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.hosta.Floricraft2.mod.TiC.ModuleFloriconstract;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.BooleanItemPropertyGetter;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ProjectileNBT;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTools;

public class ThrowingRose extends ProjectileCore {

	private static PartMaterialType petalPMT = new PartMaterialType(ModuleFloriconstract.partPetal, MaterialTypes.HEAD);
	
	public ThrowingRose()
	{
		super(PartMaterialType.arrowShaft(TinkerTools.arrowShaft), petalPMT, petalPMT, PartMaterialType.fletching(TinkerTools.fletching));
	    this.addCategory(Category.NO_MELEE, Category.PROJECTILE);
	    this.addPropertyOverride(new ResourceLocation("pulling"), new BooleanItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public boolean applyIf(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack;
            }
        });
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		if(this.isInCreativeTab(tab))
		{
			this.addDefaultSubItems(subItems, TinkerMaterials.wood, null, null, TinkerMaterials.leaf);
		}
	}
	
	@Override
	public float damagePotential()
	{
		return 0.5f;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
		if(ToolHelper.isBroken(itemStackIn))
		{
			return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
		}
		
	    ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, true);
	    if(ret != null)
	    {
	    	return ret;
	    }
	    
	    playerIn.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if (!(entityLiving instanceof EntityPlayer))
		{
			return;
		}
		
		int timeSpent = this.getMaxItemUseDuration(stack) - timeLeft;
		if (timeSpent < 5)
		{
			return;
		}
		
		int multi = Math.min(timeSpent / 20, 5);
		EntityPlayer player = (EntityPlayer) entityLiving;
		for (int i = -multi; i <= multi; i++)
		{
			if(ToolHelper.isBroken(stack))
			{
				break;
			}
			
			if(!worldIn.isRemote)
			{
				boolean usedAmmo = !player.capabilities.isCreativeMode && useAmmo(stack, player);
				EntityProjectileBase projectile = this.getProjectile(stack, stack, worldIn, player, 5f * i, 2.1f, 0f, 1f, usedAmmo);
				worldIn.spawnEntity(projectile);
			}
		}
	}

	@Override
	public EntityProjectileBase getProjectile(ItemStack stack, ItemStack launcher, World world, EntityPlayer player, float speed, float inaccuracy, float power, boolean usedAmmo)
	{
		inaccuracy *= ProjectileNBT.from(stack).accuracy;
	    return new EntityThrowingRose(world, player, speed, inaccuracy, getProjectileStack(stack, world, player, usedAmmo), launcher);
	}

	public EntityProjectileBase getProjectile(ItemStack stack, ItemStack launcher, World world, EntityPlayer player, float yaw, float speed, float inaccuracy, float power, boolean usedAmmo)
	{
		inaccuracy *= ProjectileNBT.from(stack).accuracy;
	    return new EntityThrowingRose(world, player, yaw, speed, inaccuracy, getProjectileStack(stack, world, player, usedAmmo), launcher);
	}

	@Override
	public ProjectileNBT buildTagData(List<Material> materials)
	{
		ArrowShaftMaterialStats shafts = materials.get(0).getStatsOrUnknown(MaterialTypes.SHAFT);
		HeadMaterialStats[] head = new HeadMaterialStats[]{materials.get(1).getStatsOrUnknown(MaterialTypes.HEAD), materials.get(2).getStatsOrUnknown(MaterialTypes.HEAD)};
	    FletchingMaterialStats fletching = materials.get(3).getStatsOrUnknown(MaterialTypes.FLETCHING);

	    ProjectileNBT data = new ProjectileNBT();
	    data.shafts(this, shafts);
	    data.head(head);
	    data.fletchings(fletching);
	    return data;
	}
}
