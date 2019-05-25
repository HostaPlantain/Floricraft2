package com.hosta.Floricraft2.util;

import java.util.HashMap;
import java.util.List;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.block.BlockBasicFluid;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegisterHelper {

	public static void registerBlocks(IForgeRegistry<Block> registry, List<Block> blocks)
	{
		blocks.forEach(block -> registerBlock(registry, block));
	}

	public static void registerItems(IForgeRegistry<Item> register, List<Item> items)
	{
		items.forEach(item -> registerItem(register, item));
	}

	public static void registerItemBlocks(IForgeRegistry<Item> register, List<Block> blocks)
	{
		for (Block block : blocks)
		{
			if (block instanceof IMetaName)
			{
				registerItem(register, new ItemBlockMeta(block));
			}
			else if (block instanceof BlockBasicCrops || block instanceof BlockBasicFluid)
			{
				
			}
			else
			{
				registerItem(register, new ItemBlock(block));
			}
		}
	}

	public static void registerPotions(IForgeRegistry<Potion> registry, List<Potion> potions)
	{
		potions.forEach(potion -> registerPotion(registry, potion));
	}

	public static void registerEnchantments(IForgeRegistry<Enchantment> registry, List<Enchantment> enchantments)
	{
		enchantments.forEach(enchantment -> registerEnchantment(registry, enchantment));
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id)
	{
		EntityRegistry.registerModEntity(getResourceLocation(entityName), entityClass, entityName, id, Floricraft2.fc, 64, 1, false);
	}

	/** WIP */
	public static void registerEntities(IForgeRegistry<EntityEntry> registry, List<EntityEntry> entities)
	{
		entities.forEach(entity -> registerEntity(registry, entity));
	}

	public static void registerRecipes(IForgeRegistry<IRecipe> registry, List<IRecipe> recipes)
	{
		HashMap<Item, Integer> items = new HashMap<Item, Integer>();
		int uncommon = 0;
		for (IRecipe recipe : recipes)
		{
			if (!recipe.getRecipeOutput().isEmpty())
			{
				Item output = recipe.getRecipeOutput().getItem();
				int i = !items.containsKey(output) ? 0 : items.get(output) + 1;
				registerRecipe(registry, recipe, output.getUnlocalizedName().substring(5) + "_" + i);
				items.put(output, i);
			}
			else
			{
				registerRecipe(registry, recipe, "uncommon_recipe_" + uncommon++);
			}
		}
	}

	public static void registerBrewingRecipe(List<BrewingRecipe> recipes)
	{
		recipes.forEach(recipe -> BrewingRecipeRegistry.addRecipe(recipe));
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(List<Item> items)
	{
		items.forEach(item -> registerItemRender(item));
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockRenders(List<Block> blocks)
	{
		blocks.forEach(block -> registerItemRender(Item.getItemFromBlock(block)));
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return path == null ? (ResourceLocation) null : new ResourceLocation(Reference.MOD_ID, path);
	}

	// -----Private Use Only-----//

	private static void registerBlock(IForgeRegistry<Block> registry, Block block)
	{
		block.setRegistryName(getResourceLocation(block.getUnlocalizedName().substring(5)));
		register(registry, block);
	}

	private static void registerItem(IForgeRegistry<Item> registry, Item item)
	{
		item.setRegistryName(getResourceLocation(item.getUnlocalizedName().substring(5)));
		register(registry, item);
	}

	private static void registerPotion(IForgeRegistry<Potion> registry, Potion potion)
	{
		potion.setRegistryName(getResourceLocation(potion.getName()));
		register(registry, potion);
	}

	private static void registerEnchantment(IForgeRegistry<Enchantment> registry, Enchantment enchantment)
	{
		enchantment.setRegistryName(getResourceLocation(enchantment.getName()));
		register(registry, enchantment);
	}

	private static void registerEntity(IForgeRegistry<EntityEntry> registry, EntityEntry entity)
	{
		entity.setRegistryName(getResourceLocation(entity.getName()));
		register(registry, entity);
	}

	private static void registerRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe, String id)
	{
		recipe.setRegistryName(getResourceLocation(id));
		register(registry, recipe);
	}

	@SideOnly(Side.CLIENT)
	private static void registerItemRender(Item item)
	{
		if (item == null || item == Items.AIR)
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

	private static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> registry, V object)
	{
		registry.register(object);
	}

	/**
	 * static void registerWithTileEntity(Block block, Class<? extends TileEntity>
	 * tileEntityClass) { GameRegistry.registerTileEntity(tileEntityClass,
	 * block.getUnlocalizedName()); } protected static void
	 * registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name) {
	 * biome.setRegistryName(getResourceLocation(name)); register(registry, biome);
	 * } protected static void registerBrewingRecipe(ItemStack input, ItemStack
	 * ingredient, ItemStack output) { BrewingRecipeRegistry.addRecipe(input,
	 * ingredient, output); }
	 **/
}
