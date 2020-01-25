package com.hosta.Floricraft2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public abstract class Registries {

	protected boolean							isReady	= true;
	protected final List<IForgeRegistryEntry>	LIST	= new ArrayList<>();

	public void register(IForgeRegistryEntry... entry)
	{
		if (isReady)
		{
			LIST.addAll(Helper.toList(entry));
		}
		else
		{
			// Log.warn("Too late! Registering has been failed!", entry);
		}
	}

	public void registerFinal(IForgeRegistry registry)
	{
		for (IForgeRegistryEntry entry : LIST)
		{
			registerUnnamed(registry, entry);
		}
		isReady = false;
	}

	protected <V extends IForgeRegistryEntry<V>> void registerUnnamed(IForgeRegistry<V> registry, V entry)
	{
		setRegistryName(entry);
		Registries.register(registry, entry);
	}

	protected abstract void setRegistryName(IForgeRegistryEntry entry);

	private static void setRegistryName(IForgeRegistryEntry entry, String name)
	{
		entry.setRegistryName(Reference.getResourceLocation(name));
	}

	private static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> registry, V entry)
	{
		registry.register(entry);
	}

	// -----Registries----- //

	public static final RegistryBlocks BLOCKS = new RegistryBlocks();

	public static class RegistryBlocks extends Registries {

		@Override
		protected <V extends IForgeRegistryEntry<V>> void registerUnnamed(IForgeRegistry<V> registry, V entry)
		{
			super.registerUnnamed(registry, entry);
			if (entry instanceof BlockEntityContainer)
			{
				GameRegistry.registerTileEntity(((BlockEntityContainer) entry).getTileEntityClass(), entry.getRegistryName());
			}
		}

		@Override
		protected void setRegistryName(IForgeRegistryEntry entry)
		{
			setRegistryName(entry, ((Block) entry).getUnlocalizedName().substring(5));
		}

		public void registerItems(IForgeRegistry<Item> registry)
		{
			for (IForgeRegistryEntry block : LIST)
			{
				if (!(block instanceof BlockBasicCrops) && !(block instanceof BlockBasicFluid))
				{
					Registries.ITEMS.registerUnnamed(registry, (block instanceof IMetaName) ? new ItemBlockMeta((Block) block) : new ItemBlock((Block) block));
				}
			}
		}

		@SideOnly(Side.CLIENT)
		public void registerRenders()
		{
			for (IForgeRegistryEntry block : LIST)
			{
				RegistryItems.registerRender(Item.getItemFromBlock((Block) block));

				if (block instanceof BlockEntityContainer)
				{
					BlockEntityContainer blockContainer = (BlockEntityContainer) block;
					TileEntitySpecialRenderer renderer = blockContainer.getCustomRenderer();
					if (renderer != null)
					{
						ClientRegistry.bindTileEntitySpecialRenderer(blockContainer.getTileEntityClass(), renderer);
					}
				}
				else if (block instanceof BlockBasicFluid)
				{
					ModelLoader.setCustomStateMapper((Block) block, new StateMapperBase()
					{
						@Override
						protected ModelResourceLocation getModelResourceLocation(IBlockState state)
						{
							return new ModelResourceLocation(block.getRegistryName().toString());
						}
					});
				}
			}
		}
	};

	public static final RegistryItems ITEMS = new RegistryItems();

	public static class RegistryItems extends Registries {

		@SideOnly(Side.CLIENT)
		public void registerRenders()
		{
			LIST.forEach(item -> registerRender((Item) item));
		}

		@Override
		protected void setRegistryName(IForgeRegistryEntry entry)
		{
			setRegistryName(entry, ((Item) entry).getUnlocalizedName().substring(5));
		}

		@SideOnly(Side.CLIENT)
		public static void registerRender(Item item)
		{
			if (item == null || item == Items.AIR)
			{
				return;
			}

			String name = item.getRegistryName().toString();
			if (item instanceof IMetaName)
			{
				IMetaName iMeta = (IMetaName) item;
				for (int i = 0; i < iMeta.countSubItems(); i++)
				{
					ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(name + "_" + iMeta.getSubName(i), "inventory"));
				}
			}
			else
			{
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
			}
		}
	}

	public static final Registries		POTIONS			= new Registries()
														{
															@Override
															protected void setRegistryName(IForgeRegistryEntry entry)
															{
																setRegistryName(entry, ((Potion) entry).getName());
															}
														};

	public static final Registries		ENCHANTMENTS	= new Registries()
														{
															@Override
															protected void setRegistryName(IForgeRegistryEntry entry)
															{
																setRegistryName(entry, ((Enchantment) entry).getName());
															}
														};

	public static final RegitryRecipes	RECIPES			= new RegitryRecipes();

	public static class RegitryRecipes extends Registries {

		private final HashMap<Item, Integer>	ITEMS		= new HashMap<Item, Integer>();
		int										uncommon	= 0;

		private final List<BrewingRecipe>		BREINGS		= new ArrayList<>();

		@Override
		public void registerFinal(IForgeRegistry registry)
		{
			BREINGS.forEach(recipe -> BrewingRecipeRegistry.addRecipe(recipe));
			super.registerFinal(registry);
		}

		@Override
		protected void setRegistryName(IForgeRegistryEntry entry)
		{
			IRecipe recipe = (IRecipe) entry;
			if (!recipe.getRecipeOutput().isEmpty())
			{
				Item output = recipe.getRecipeOutput().getItem();
				int i = !ITEMS.containsKey(output) ? 0 : ITEMS.get(output) + 1;
				ITEMS.put(output, i);

				recipe.setRegistryName(Reference.getResourceLocation(output.getUnlocalizedName().substring(5) + "_" + i));
			}
			else
			{
				recipe.setRegistryName(Reference.getResourceLocation("uncommon_recipe_" + uncommon++));
			}
		}

		public void register(BrewingRecipe... recipes)
		{
			if (isReady)
			{
				BREINGS.addAll(Helper.toList(recipes));
			}
			else
			{
				// Log.warn("Too late! Registering has been failed!", entry);
			}
		}
	}

	// -----Entity----- //
	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id)
	{
		EntityRegistry.registerModEntity(Reference.getResourceLocation(entityName), entityClass, entityName, id, Floricraft2.fc, 64, 1, false);
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
}
