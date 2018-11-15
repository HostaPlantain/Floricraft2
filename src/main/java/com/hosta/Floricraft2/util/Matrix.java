package com.hosta.Floricraft2.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Matrix {

	public float x;
	public float y;
	public float z;
	
	public Matrix(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
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
		
		float x = (float) ((vec.x * vec.x * (1 - cos) + cos) * this.x
				+ (vec.x * vec.y * (1 - cos) - (vec.z * sin)) * this.y
				+ (vec.x * vec.z * (1 - cos) + (vec.y * sin)) * this.z);
		
		float y = (float) ((vec.x * vec.y * (1 - cos) + (vec.z * sin)) * this.x
				+ (vec.y * vec.y * (1 - cos) + cos) * this.y
				+ (vec.y * vec.z * (1 - cos) - (vec.x * sin)) * this.z);
		
		float z = (float) ((vec.x * vec.z * (1 - cos) - (vec.y * sin)) * this.x
				+ (vec.y * vec.z * (1 - cos) + (vec.x * sin)) * this.y
				+ (vec.z * vec.z * (1 - cos) + cos) * this.z);
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static float radian(float angle)
	{
		return (float) (angle / 180.0F * Math.PI);
	}
}
