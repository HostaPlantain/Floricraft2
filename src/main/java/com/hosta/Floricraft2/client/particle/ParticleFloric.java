package com.hosta.Floricraft2.client.particle;

import com.hosta.Floricraft2.module.ModuleFlowers;
import com.hosta.Floricraft2.world.Wind;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleFloric extends ParticleBase {

	public ParticleFloric(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, TextureAtlasSprite texture, boolean genByBlock)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.setMaxAge(1024);
		this.particleGravity = 0.07f;
		this.particleScale = 0.5f;
		this.setParticleTexture(texture);

		float value = 2 * (float) Math.PI * rand.nextFloat();
		this.motionX = MathHelper.cos(value) * (genByBlock ? 0.01D : 0.1D);
		this.motionY = genByBlock ? -(double) this.particleGravity : 0.06D * rand.nextFloat() + 0.07D;
		this.motionZ = MathHelper.sin(value) * (genByBlock ? 0.01D : 0.1D);
	}

	public ParticleFloric(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, int meta, boolean genByBlock)
	{
		this(worldIn, xCoordIn, yCoordIn, zCoordIn, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(ModuleFlowers.PETAL_RAW, meta), genByBlock);
	}

	public ParticleFloric(World worldIn, Vec3d pos, int meta, boolean genByBlock)
	{
		this(worldIn, pos.x, pos.y, pos.z, meta, genByBlock);
	}

	@Override
	public int getFXLayer()
	{
		return 1;
	}

	@Override
	public void onUpdate()
	{
		this.onGround = this.posY == this.prevPosY && this.particleAge > 10;

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}

		double[] wind = Wind.getWind(this.world);

		IBlockState onBlock = this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
		if (onBlock.getMaterial().isLiquid())
		{
			double waterLevel = (double) (1.0F - BlockLiquid.getLiquidHeightPercent(((Integer) onBlock.getValue(BlockLiquid.LEVEL)).intValue()));
			boolean isAbove = (double) MathHelper.floor(this.posY) + waterLevel + 0.1D < this.posY;
			boolean isBelow = (double) MathHelper.floor(this.posY) + waterLevel > this.posY || this.world.getBlockState(new BlockPos(this.posX, this.posY + 1, this.posZ)).getMaterial().isLiquid();

			wind[0] *= isBelow ? 0.0D : 0.1D;
			wind[1] = 0.0D;
			wind[2] *= isBelow ? 0.0D : 0.1D;
			this.motionX = 0.0D;
			this.motionY = isAbove || isBelow ? (motionY < 0.0D ? motionY : -(double) this.particleGravity * 0.05D) : 0.0D;
			this.motionZ = 0.0D;
		}
		else if (this.onGround)
		{
			this.motionX *= 0.5D;
			this.motionY = -(double) this.particleGravity;
			this.motionZ *= 0.5D;

			if (wind[1] <= 0.0D)
			{
				wind[0] = 0.0D;
				wind[2] = 0.0D;
			}

			this.particleAge += 4;
		}
		else
		{
			if (this.particleAge < 5)
			{
				this.motionX *= 0.9D;
				this.motionY *= 0.9D;
				this.motionZ *= 0.9D;
			}
			else if (this.particleAge < 10)
			{
				this.motionX *= 0.8D;
				this.motionY *= 0.8D;
				this.motionZ *= 0.8D;
			}
			else if (this.particleAge % 20 == 10)
			{
				float value = 2 * (float) Math.PI * rand.nextFloat();
				this.motionX = MathHelper.cos(value) * 0.01D;
				this.motionY = -(double) this.particleGravity;
				this.motionZ = MathHelper.sin(value) * 0.01D;
			}
			else
			{
				this.motionX *= 1.1D;
				this.motionY += 0.05D * (double) this.particleGravity;
				this.motionZ *= 1.1D;
			}
		}

		this.move(this.motionX + wind[0], this.motionY + wind[1], this.motionZ + wind[2]);
	}
}
