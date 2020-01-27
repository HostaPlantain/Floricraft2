package com.hosta.Floricraft2.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Matrix {

	public float	x;
	public float	y;
	public float	z;

	public Matrix(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Matrix(double x, double y, double z)
	{
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
	}

	public void rotateLook(Entity entity)
	{
		this.rotate(Matrix.radian(entity.rotationYaw), Matrix.radian(entity.rotationPitch));
	}

	public void rotate(float yaw, float pitch)
	{
		Vec3d vec = new Vec3d(0, -1, 0);
		this.rotate(yaw, vec);

		vec = new Vec3d(MathHelper.cos(yaw), 0, MathHelper.sin(yaw));
		this.rotate(pitch, vec);
	}

	public void rotate(float angle, Vec3d vec)
	{
		float cos = MathHelper.cos(angle);
		float sin = MathHelper.sin(angle);

		float x = (float) ((vec.x * vec.x * (1 - cos) + cos) * this.x + (vec.x * vec.y * (1 - cos) - (vec.z * sin)) * this.y + (vec.x * vec.z * (1 - cos) + (vec.y * sin)) * this.z);

		float y = (float) ((vec.x * vec.y * (1 - cos) + (vec.z * sin)) * this.x + (vec.y * vec.y * (1 - cos) + cos) * this.y + (vec.y * vec.z * (1 - cos) - (vec.x * sin)) * this.z);

		float z = (float) ((vec.x * vec.z * (1 - cos) - (vec.y * sin)) * this.x + (vec.y * vec.z * (1 - cos) + (vec.x * sin)) * this.y + (vec.z * vec.z * (1 - cos) + cos) * this.z);

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void marge(Matrix mat)
	{
		this.x += mat.x;
		this.y += mat.y;
		this.z += mat.z;
	}

	public void multipy(float f)
	{
		this.x *= f;
		this.y *= f;
		this.z *= f;
	}

	public void divide(float f)
	{
		this.x /= f;
		this.y /= f;
		this.z /= f;
	}

	public BlockPos move(BlockPos pos)
	{
		return new BlockPos(this.x + pos.getX(), this.y + pos.getY(), this.z + pos.getZ());
	}

	public static Matrix getDefault()
	{
		return new Matrix(0, 0, 1);
	}

	public static float radian(float angle)
	{
		return (float) (angle / 180.0F * Math.PI);
	}
}
