package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.item.IMetaName;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Module {

	protected static void registerItem(IForgeRegistry<Item> registry, Item item)
	{
		item.setRegistryName(new ResourceLocation(Reference.MOD_ID, item.getUnlocalizedName().substring(5)));
		Module.register(registry, item);
	}
	
	protected static void registerBlock(IForgeRegistry<Block> registry, Block block)
	{
		block.setRegistryName(new ResourceLocation(Reference.MOD_ID, block.getUnlocalizedName().substring(5)));
		Module.register(registry, block);
	}

	protected static void registerWithTileEntity(Block block, Class<? extends TileEntity> tileEntityClass)
	{
		GameRegistry.registerTileEntity(tileEntityClass, block.getUnlocalizedName());
	}

	protected static void registerPotion(IForgeRegistry<Potion> registry, Potion potion)
	{
		potion.setRegistryName(new ResourceLocation(Reference.MOD_ID, potion.getName()));
		Module.register(registry, potion);
	}

	protected static void registerEnchantment(IForgeRegistry<Enchantment> registry, Enchantment enchantment)
	{
		enchantment.setRegistryName(new ResourceLocation(Reference.MOD_ID, enchantment.getName()));
		Module.register(registry, enchantment);
	}

	protected static void registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name)
	{
		biome.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
		Module.register(registry, biome);
	}

	protected static void registerRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe, String id)
	{
		recipe.setRegistryName(new ResourceLocation(Reference.MOD_ID, id));
		Module.register(registry, recipe);
	}
	
	protected static void registerEntity(Class<? extends Entity> entityClass, String entityName)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, entityName), entityClass, entityName, ModuleOthers.entityId++, Floricraft2.instance, 64, 1, false);
	}
	
	private static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> registry, V object)
	{
		registry.register(object);
	}
	
	@SideOnly(Side.CLIENT)
	protected static void registerItemRender(Item item)
	{
		if (item == Items.AIR)
		{
			return;
		}
		
		if (item instanceof IMetaName)
		{
			IMetaName iMeta = (IMetaName) item;
			for (int i = 0; i < iMeta.countSubItems(); i++)
			{
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5) + "_" + iMeta.getSubName(i), "inventory"));
			}
		}
		else
		{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
		}
	}
}
