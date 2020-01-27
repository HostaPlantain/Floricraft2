package com.hosta.Floricraft2.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockBaseFluid extends BlockFluidClassic {

	public BlockBaseFluid(String name, Fluid fluid, Material material)
	{
		super(fluid, material);
		this.setUnlocalizedName(name);
	}

}
