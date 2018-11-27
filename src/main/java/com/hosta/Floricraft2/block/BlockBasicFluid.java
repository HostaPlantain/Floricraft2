package com.hosta.Floricraft2.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockBasicFluid extends BlockFluidClassic {
	
	public BlockBasicFluid (String name, Fluid fluid, Material material)
	{
		super(fluid, material);
		this.setUnlocalizedName(name);
	}

}
