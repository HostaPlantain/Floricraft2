package com.hosta.Floricraft2.legacy;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.item.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Registerer {
	
	//Register
	public static void register(Item item)
	{
		Registerer.registerGameRegistry(item, new ResourceLocation(Reference.MOD_ID, item.getUnlocalizedName().substring(5)));
	}
	
	public static void register(Block block)
	{
		Registerer.registerGameRegistry(block);
		Registerer.registerGameRegistry(new ItemBlock(block), block.getRegistryName());
	}

	public static void registerWithMeta(Block block)
	{
		Registerer.registerGameRegistry(block);
		Registerer.registerGameRegistry(new ItemBlockMeta(block), block.getRegistryName());
	}

	public static void registerWithTileEntity(Block block, Class<? extends TileEntity> tileEntityClass)
	{
		Registerer.register(block);
		GameRegistry.registerTileEntity(tileEntityClass, block.getUnlocalizedName());
	}

	public static void registerWithTileEntityWithMeta(Block block, Class<? extends TileEntity> tileEntityClass)
	{
		Registerer.registerWithMeta(block);
		GameRegistry.registerTileEntity(tileEntityClass, block.getUnlocalizedName());
	}
	
	public static void registerBlockWithOutItem(Block block)
	{
		Registerer.registerGameRegistry(block);
	}

	private static void registerGameRegistry(Block block)
	{
		block.setRegistryName(new ResourceLocation(Reference.MOD_ID, block.getUnlocalizedName().substring(5)));
		ForgeRegistries.BLOCKS.register(block);
	}

	private static void registerGameRegistry(Item item, ResourceLocation name)
	{
		item.setRegistryName(name);
		ForgeRegistries.ITEMS.register(item);
	}
	
	public static void register(Potion potion)
	{
		potion.setRegistryName(new ResourceLocation(Reference.MOD_ID, potion.getName()));
		ForgeRegistries.POTIONS.register(potion);
	}

	public static void register(Enchantment enchantment)
	{
		enchantment.setRegistryName(new ResourceLocation(Reference.MOD_ID, enchantment.getName()));
		ForgeRegistries.ENCHANTMENTS.register(enchantment);
	}

	public static void register(Biome biome, String name)
	{
		biome.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		ForgeRegistries.BIOMES.register(biome);
	}

	public static void register(IRecipe recipe, String ID)
	{
		recipe.setRegistryName(new ResourceLocation(Reference.MOD_ID, ID));
		ForgeRegistries.RECIPES.register(recipe);
	}

	public static void register(Class<? extends Entity> entity, String name, int id)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, name), entity, name, id, Floricraft2.instance, 80, 3, true);
	}
}
