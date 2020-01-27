package com.hosta.Floricraft2.entity;

import java.util.List;

import com.hosta.Floricraft2.util.Matrix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBaseProjectile extends Entity implements IProjectile {

	private EntityLivingBase shooter = null;
	public EntityBaseProjectile(World worldIn)
	{
		super(worldIn);
        this.setSize(0.5F, 0.5F);
	}

	public void shootByShooter(EntityLivingBase entity, float velocity, float inaccuracy)
	{
		this.shooter = entity;
		Matrix mat = Matrix.getDefault();
		mat.rotate(Matrix.radian(entity.rotationYawHead), Matrix.radian(entity.rotationPitch));
		mat.marge(new Matrix(entity.motionX, entity.motionY, entity.motionZ));
		this.setPosition(entity.posX + mat.x, entity.posY + entity.getEyeHeight() + mat.y, entity.posZ + mat.z);
		this.shoot(mat, velocity);
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy)
	{
		Matrix mat = new Matrix(x, y, z);
		mat.divide(MathHelper.sqrt(x * x + y * y + z * z));
		this.shoot(mat, velocity);
	}

	private void shoot(Matrix mat, float velocity)
	{
		mat.multipy(velocity);
		this.motionX = mat.x;
		this.motionY = mat.y;
		this.motionZ = mat.z;
		this.setYawPitchFromMotion();
	}

	private void setYawPitchFromMotion()
	{
		float sqrt = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(this.motionY, sqrt) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	protected List<Entity> hitEntities()
	{
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
		if (shooter != null)
		{
			list.remove(shooter);
		}
		return list;
	}

	protected boolean isStucked()
	{
		return !this.hitEntities().isEmpty() || this.onGround || this.collided;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if (!this.isStucked())
		{
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		}
		else
		{
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
		}
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) { }
}
