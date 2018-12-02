package com.hosta.Floricraft2.mod.Thaum;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.research.ResearchCategories;

public class ModuleFloralia extends Module{
	
	private static final List<Item> ITEMS = new ArrayList<Item>();
	private static final List<Block> BLOCKS = new ArrayList<Block>();
	static
	{
		
	}
	
	public static final Aspect FLOWER = new Aspect("flos", 0xFFADAD, new Aspect[] {Aspect.PLANT, Aspect.AIR}, getResourceLocation("textures/aspects/"+"flos"+".png"), 1);
	public static final Aspect FAIRY = new Aspect("fata", ModuleOthers.COLOR_FLORIC, new Aspect[] {Aspect.MAGIC, Aspect.FLIGHT}, getResourceLocation("textures/aspects/"+"nympha"+".png"), 1);

	@SubscribeEvent
	public void registerAspects(AspectRegistryEvent event)
	{
		AspectEventProxy register = event.register;
		//Flower
		registerAspect(new ItemStack(ModuleItems.CUT_FLOWER,  1, OreDictionary.WILDCARD_VALUE),		new AspectList().add(FLOWER, 5).add(Aspect.PLANT, 1), register);
		registerAspect(new ItemStack(ModuleItems.PETAL_RAW,  1, OreDictionary.WILDCARD_VALUE),		new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1), register);
		registerAspect(new ItemStack(ModuleItems.PETAL_DRY,  1, OreDictionary.WILDCARD_VALUE),		new AspectList().add(FLOWER, 3), register);
		registerAspect(new ItemStack(ModuleItems.PETAL_SALTY,  1, OreDictionary.WILDCARD_VALUE),	new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1).add(Aspect.CRAFT, 1), register);
		registerAspect(new ItemStack(ModuleItems.PETAL_SUGARED,  1, OreDictionary.WILDCARD_VALUE),	new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1).add(Aspect.LIFE, 1), register);
		for (Block stack : ModuleItems.STACK_FLOWER)
		{
			registerAspect(new ItemStack(stack, 1, 3),	new AspectList().add(FLOWER, 10), register);
		}
		registerAspect(ModuleItems.STACK_DEAD,			new AspectList().add(Aspect.DEATH, 5).add(Aspect.PLANT, 1), register);
		//Hemp
		registerAspect(ModuleItems.SEED_HEMP,			new AspectList().add(Aspect.PLANT, 5), register);
		registerAspect(ModuleItems.HEMP_YARN,			new AspectList().add(Aspect.PLANT, 3).add(Aspect.CRAFT, 1), register);
		//Thaum
		registerAspect(ModuleItems.INGOT_TWINKLE,		new AspectList(new ItemStack(Items.IRON_INGOT)).add(FAIRY, 5), register);
	}
	
	@Override
	public List<IRecipe> registerRecipes()
	{
		List<IRecipe> recipes = new ArrayList<IRecipe>();

		return recipes;
	}
	
	@Override
	public void init()
	{
		registerCrucibeRecipes();
		registerFakeRecipes();
		registerResearches();
	}
	
	private void registerCrucibeRecipes()
	{
		registerCrucibeRecipe("ingot_twinkle", "INGOT_TWINKLE", new ItemStack(ModuleItems.INGOT_TWINKLE), new ItemStack(Items.IRON_INGOT), new AspectList().add(FAIRY, 5).add(Aspect.MAGIC, 5));
	}
	
	private void registerFakeRecipes()
	{
		for (IRecipe recipe : GameRegistry.findRegistry(IRecipe.class).getValues())
		{
			Item output = recipe.getRecipeOutput().getItem();
			if (output == ModuleItems.NUGGET_TWINKLE || output == Item.getItemFromBlock(ModuleItems.BLOCK_TWINKLE))
			{
				ThaumcraftApi.addFakeCraftingRecipe(getResourceLocation(output.getUnlocalizedName().substring(5) + "_fake"), recipe);
			}
		}
	}
	
	private void registerResearches()
	{
		ResearchCategories.registerCategory("FLORALIA", null, new AspectList(), getResourceLocation("textures/items/cut_flower_rose.png"), getResourceLocation("textures/gui/gui_floralia_back_1.jpg"), new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));
		ThaumcraftApi.registerResearchLocation(getResourceLocation("research/floralia"));
	}
	
	private static void registerAspect(Item item, AspectList aspects, AspectEventProxy register)	{registerAspect(new ItemStack(item), aspects, register);}
	private static void registerAspect(Block block, AspectList aspects, AspectEventProxy register)	{registerAspect(new ItemStack(block), aspects, register);}
	private static void registerAspect(ItemStack itemstack, AspectList aspects, AspectEventProxy register)
	{
		register.registerObjectTag(itemstack, aspects);
	}
	private static void registerAspect(String oreDict, AspectList aspects, AspectEventProxy register)	{register.registerObjectTag(oreDict, aspects);}

	private static void registerAddAspect(Item item, AspectList aspects, AspectEventProxy register)	{registerAddAspect(new ItemStack(item), aspects, register);}
	private static void registerAddAspect(Block block, AspectList aspects, AspectEventProxy register)	{registerAddAspect(new ItemStack(block), aspects, register);}
	private static void registerAddAspect(ItemStack itemstack, AspectList aspects, AspectEventProxy register)
	{
		register.registerComplexObjectTag(itemstack, aspects);
	}
	private static void registerAddAspect(String oreDict, AspectList aspects, AspectEventProxy register)	{register.registerComplexObjectTag(oreDict, aspects);}
	
	private void registerCrucibeRecipe(String path, String key, ItemStack result, ItemStack recipe, AspectList aspects)
	{
		ThaumcraftApi.addCrucibleRecipe(getResourceLocation(path), new CrucibleRecipe(key, result, recipe, aspects));
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		registerItems(event.getRegistry(), ITEMS);
		registerItemBlocks(event.getRegistry(), BLOCKS);
	}

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		registerBlocks(event.getRegistry(), BLOCKS);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		registerItemRenders(ITEMS);
		registerItemBlockRenders(BLOCKS);
	}
	
	@Override
	public void postInit()
	{
		ITEMS.clear();
		BLOCKS.clear();
	}
}
