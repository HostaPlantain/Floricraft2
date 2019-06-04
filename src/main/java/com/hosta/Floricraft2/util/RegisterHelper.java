package com.hosta.Floricraft2.util;

import java.util.HashMap;
import java.util.List;

import org.jline.utils.Log;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.block.BlockBasicCrops;
import com.hosta.Floricraft2.block.BlockBasicFluid;
import com.hosta.Floricraft2.block.BlockEntityContainer;
import com.hosta.Floricraft2.item.IMetaName;
import com.hosta.Floricraft2.item.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegisterHelper {

	public static void warn(Object entry)
	{
		Log.warn("Too late! Registering has been faled!", entry);
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return path == null ? (ResourceLocation) null : new ResourceLocation(Reference.MOD_ID, path);
	}

	private static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> registry, V object)
	{
		registry.register(object);
	}

	// -----Block----- //

	public static void registerBlocks(IForgeRegistry<Block> registry, List<Block> blocks)
	{
		blocks.forEach(block ->
		{
			RegisterHelper.registerBlock(registry, block);

			if (block instanceof BlockEntityContainer)
			{
				RegisterHelper.registerTileEntity((BlockEntityContainer) block);
			}
		});
	}

	private static void registerBlock(IForgeRegistry<Block> registry, Block block)
	{
		block.setRegistryName(RegisterHelper.getResourceLocation(block.getUnlocalizedName().substring(5)));
		RegisterHelper.register(registry, block);
	}

	private static void registerTileEntity(BlockEntityContainer block)
	{
		GameRegistry.registerTileEntity(block.getTileEntityClass(), RegisterHelper.getResourceLocation(block.getUnlocalizedName().substring(5)));
	}

	// -----Item----- //

	public static void registerItems(IForgeRegistry<Item> register, List<Item> items)
	{
		items.forEach(item -> registerItem(register, item));
	}

	public static void registerItemBlocks(IForgeRegistry<Item> register, List<Block> blocks)
	{
		blocks.forEach(block ->
		{
			if (block instanceof BlockBasicCrops || block instanceof BlockBasicFluid)
			{

			}
			else if (block instanceof IMetaName)
			{
				RegisterHelper.registerItem(register, new ItemBlockMeta(block));
			}
			else
			{
				RegisterHelper.registerItem(register, new ItemBlock(block));
			}
		});
	}

	private static void registerItem(IForgeRegistry<Item> registry, Item item)
	{
		item.setRegistryName(RegisterHelper.getResourceLocation(item.getUnlocalizedName().substring(5)));
		RegisterHelper.register(registry, item);
	}

	// -----Item Render----- //

	@SideOnly(Side.CLIENT)
	public static void registerItemRenders(List<Item> items)
	{
		items.forEach(item -> RegisterHelper.registerItemRender(item));
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockRenders(List<Block> blocks)
	{
		blocks.forEach(block ->
		{
			registerItemRender(Item.getItemFromBlock(block));
			
			if (block instanceof BlockEntityContainer)
			{
				BlockEntityContainer blockContainer = (BlockEntityContainer) block;
				TileEntitySpecialRenderer renderer = blockContainer.getCustomRenderer();
				if (renderer != null)
				{
					RegisterHelper.registerRender(blockContainer.getTileEntityClass(), renderer);
				}
			}
			else if (block instanceof BlockBasicFluid)
			{
				ModelLoader.setCustomStateMapper(block, new StateMapperBase()
				{
					@Override
					protected ModelResourceLocation getModelResourceLocation(IBlockState state)
					{
						return new ModelResourceLocation(block.getRegistryName().toString());
					}
				});
			}
		});
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

	@SideOnly(Side.CLIENT)
	private static <T extends TileEntity> void registerRender(Class<T> tileEntity, TileEntitySpecialRenderer<T> renderer)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntity, renderer);
	}

	// -----Recipe----- //

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
				items.put(output, i);

				RegisterHelper.registerRecipe(registry, recipe, output.getUnlocalizedName().substring(5) + "_" + i);
			}
			else
			{
				RegisterHelper.registerRecipe(registry, recipe, "uncommon_recipe_" + uncommon++);
			}
		}
	}

	private static void registerRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe, String id)
	{
		recipe.setRegistryName(RegisterHelper.getResourceLocation(id));
		RegisterHelper.register(registry, recipe);
	}

	public static void registerBrewingRecipe(List<BrewingRecipe> recipes)
	{
		recipes.forEach(recipe -> BrewingRecipeRegistry.addRecipe(recipe));
	}

	// -----Potion----- //

	public static void registerPotions(IForgeRegistry<Potion> registry, List<Potion> potions)
	{
		potions.forEach(potion -> RegisterHelper.registerPotion(registry, potion));
	}

	private static void registerPotion(IForgeRegistry<Potion> registry, Potion potion)
	{
		potion.setRegistryName(RegisterHelper.getResourceLocation(potion.getName()));
		RegisterHelper.register(registry, potion);
	}

	// -----Enchantment----- //

	public static void registerEnchantments(IForgeRegistry<Enchantment> registry, List<Enchantment> enchantments)
	{
		enchantments.forEach(enchantment -> RegisterHelper.registerEnchantment(registry, enchantment));
	}

	private static void registerEnchantment(IForgeRegistry<Enchantment> registry, Enchantment enchantment)
	{
		enchantment.setRegistryName(RegisterHelper.getResourceLocation(enchantment.getName()));
		RegisterHelper.register(registry, enchantment);
	}

	// -----Entity----- //

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id)
	{
		EntityRegistry.registerModEntity(RegisterHelper.getResourceLocation(entityName), entityClass, entityName, id, Floricraft2.fc, 64, 1, false);
	}

	// -----WIP----- //

	// public static void registerEntities(IForgeRegistry<EntityEntry> registry, List<EntityEntry> entities)
	// {
	// entities.forEach(entity -> registerEntity(registry, entity));
	// }

	// private static void registerEntity(IForgeRegistry<EntityEntry> registry, EntityEntry entity)
	// {
	// entity.setRegistryName(RegisterHelper.getResourceLocation(entity.getName()));
	// RegisterHelper.register(registry, entity);
	// }

	// protected static void registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name)
	// {
	// biome.setRegistryName(getResourceLocation(name));
	// RegisterHelper.register(registry, biome);
	// }

	// protected static void registerBrewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output)
	// {
	// BrewingRecipeRegistry.addRecipe(input, ingredient, output);
	// }
}
