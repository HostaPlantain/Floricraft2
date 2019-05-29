package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.block.doll.BlockDollIronSit;
import com.hosta.Floricraft2.block.doll.BlockDollPlayer;

import net.minecraft.block.Block;

public class ModuleDolls implements IModule {

	// Item Usable
	// public static final Item ITEM_BALLON;

	// Doll
	public static final Block	DOLL_IRON	= new BlockDollIronSit("doll_iron");
	public static final Block	DOLL_PLAYER	= new BlockDollPlayer("doll_player");

	// Wether
	// public static final Block WEATHER_COCK;

	@Override
	public void registerBlocks()
	{
		register(DOLL_IRON, DOLL_PLAYER);
	}

	@Override
	public void registerRecipes()
	{

	}
}
