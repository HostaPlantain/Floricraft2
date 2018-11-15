package com.hosta.Floricraft2.mod.TiC.ranged;

import com.hosta.Floricraft2.util.Matrix;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.IProjectileTrait;

public class EntityThrowingRose extends EntityProjectileBase {

	public EntityThrowingRose(World world)
	{
		super(world);
	}

	public EntityThrowingRose(World world, double d, double d1, double d2)
	{
	    super(world, d, d1, d2);
	}

	public EntityThrowingRose(World world, EntityPlayer player, float speed, float inaccuracy, ItemStack stack, ItemStack launchingStack)
	{
		super(world, player, speed, inaccuracy, 1f, stack, launchingStack);
	}

	public EntityThrowingRose(World world, EntityPlayer player, float yaw, float speed, float inaccuracy, ItemStack stack, ItemStack launchingStack)
	{
		this(world);

	    this.shootingEntity = player;
	    pickupStatus = player.isCreative() ? PickupStatus.CREATIVE_ONLY : PickupStatus.ALLOWED;

	    this.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw + yaw, player.rotationPitch);
	    this.setPosition(this.posX, this.posY, this.posZ);
	    
	    Matrix mat = new Matrix(-MathHelper.sin(Matrix.radian(yaw)), 0, MathHelper.cos(Matrix.radian(yaw)));
	    mat.rotate(Matrix.radian(player.rotationYaw), Matrix.radian(player.rotationPitch));
	    
	    this.motionX = mat.x;
	    this.motionZ = mat.z;
	    this.motionY = mat.y;
	    this.shoot(this.motionX, this.motionY, this.motionZ, speed, inaccuracy);

        this.rotationYaw = -player.rotationYaw;
        this.prevRotationYaw = this.rotationYaw;
	    
	    tinkerProjectile.setItemStack(stack);
	    tinkerProjectile.setLaunchingStack(launchingStack);
	    tinkerProjectile.setPower(1f);

	    for(IProjectileTrait trait : tinkerProjectile.getProjectileTraits())
	    {
	      trait.onLaunch(this, world, player);
	    }
	}

	@Override
	protected void init()
	{
		setSize(0.3f, 0.1f);
	    this.bounceOnNoDamage = false;
	}
	
	@Override
	protected void playHitEntitySound()	{ }
	
	@Override
	protected void playHitBlockSound(float speed, IBlockState state)
	{
		this.playSound(Sounds.wood_hit, 0.5f, 1f);
	}
}
