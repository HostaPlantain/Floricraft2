package com.hosta.Floricraft2.module;

import java.util.List;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.item.IMetaName;
import com.hosta.Floricraft2.item.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Module {

	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	public void init()	{}	
	public void postInit()	{}
	
	protected static void registerItems (IForgeRegistry<Item> register, List<Item> items)
	{
		items.forEach(item -> registerItem(register, item));
	}
	protected static void registerItemBlocks (IForgeRegistry<Item> register, List<Block> blocks)
	{
		for (Block block : blocks)
		{
			if (block instanceof IMetaName)	{registerItem(register, new ItemBlockMeta(block));}
			else if (block instanceof BlockBasicCrops)	{	}
			else	{registerItem(register, new ItemBlock(block));}
		}
	}
	private static void registerItem(IForgeRegistry<Item> registry, Item item)
	{
		item.setRegistryName(getResourceLocation(item.getUnlocalizedName().substring(5)));
		register(registry, item);
	}
	
	protected static void registerBlocks(IForgeRegistry<Block> registry, List<Block> blocks)
	{
		blocks.forEach(block -> registerBlock(registry, block));
	}
	private static void registerBlock(IForgeRegistry<Block> registry, Block block)
	{
		block.setRegistryName(getResourceLocation(block.getUnlocalizedName().substring(5)));
		register(registry, block);
	}

	protected static void registerWithTileEntity(Block block, Class<? extends TileEntity> tileEntityClass)
	{
		GameRegistry.registerTileEntity(tileEntityClass, block.getUnlocalizedName());
	}

	protected static void registerPotion(IForgeRegistry<Potion> registry, List<Potion> potions)
	{
		potions.forEach(potion -> registerPotion(registry, potion));
	}
	private static void registerPotion(IForgeRegistry<Potion> registry, Potion potion)
	{
		potion.setRegistryName(getResourceLocation(potion.getName()));
		register(registry, potion);
	}

	protected static void registerEnchantments(IForgeRegistry<Enchantment> registry, List<Enchantment> enchantments)
	{
		enchantments.forEach(enchantment -> registerEnchantment(registry, enchantment));
	}
	private static void registerEnchantment(IForgeRegistry<Enchantment> registry, Enchantment enchantment)
	{
		enchantment.setRegistryName(getResourceLocation(enchantment.getName()));
		register(registry, enchantment);
	}

	protected static void registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name)
	{
		biome.setRegistryName(getResourceLocation(name));
		register(registry, biome);
	}

	protected static void registerRecipes(IForgeRegistry<IRecipe> registry, List<IRecipe> recipes)
	{
		int count = 0;
		for (IRecipe recipe : recipes)
		{
			registerRecipe(registry, recipe, recipe.getRecipeOutput().getUnlocalizedName().substring(5) + "_" + count++);
		}
	}
	private static void registerRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe, String id)
	{
		recipe.setRegistryName(getResourceLocation(id));
		register(registry, recipe);
	}
	
	protected static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id)
	{
		EntityRegistry.registerModEntity(getResourceLocation(entityName), entityClass, entityName, id, Floricraft2.instance, 64, 1, false);
	}
	
	private static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> registry, V object)
	{
		registry.register(object);
	}
	
	protected static ResourceLocation getResourceLocation(String path)
	{
		return path == null ? (ResourceLocation)null : new ResourceLocation(Reference.MOD_ID, path);
	}
	
	@SideOnly(Side.CLIENT)
	protected static void registerItemRenders(List<Item> items)
	{
		items.forEach(item -> registerItemRender(item));
	}
	@SideOnly(Side.CLIENT)
	protected static void registerItemBlockRenders(List<Block> blocks)
	{
		blocks.forEach(block -> registerItemRender(Item.getItemFromBlock(block)));
	}
	@SideOnly(Side.CLIENT)
	private static void registerItemRender(Item item)
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
