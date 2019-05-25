package com.hosta.Floricraft2.mod.Thaum;

import com.hosta.Floricraft2.module.IModule;
import com.hosta.Floricraft2.module.ModuleCrops;
import com.hosta.Floricraft2.module.ModuleFlowering;
import com.hosta.Floricraft2.module.ModuleMaterials;
import com.hosta.Floricraft2.module.ModuleOthers;
import com.hosta.Floricraft2.util.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.Thaumcraft;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.research.ResearchCategories;

public class ModuleFloralia implements IModule {

	public static final Aspect	FLOWER	= new Aspect("flos", 0xFFADAD, new Aspect[] { Aspect.PLANT, Aspect.AIR }, RegisterHelper.getResourceLocation("textures/aspects/" + "flos" + ".png"), 1);
	public static final Aspect	FAIRY	= new Aspect("fata", ModuleOthers.COLOR_FLORIC, new Aspect[] { Aspect.MAGIC, Aspect.FLIGHT }, RegisterHelper.getResourceLocation("textures/aspects/" + "nympha" + ".png"), 1);

	@Override
	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerAspects(AspectRegistryEvent event)
	{
		AspectEventProxy register = event.register;
		// Flower
		registerAspect(new ItemStack(ModuleFlowering.CUT_FLOWER, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(FLOWER, 5).add(Aspect.PLANT, 1), register);
		registerAspect(new ItemStack(ModuleFlowering.PETAL_RAW, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1), register);
		registerAspect(new ItemStack(ModuleFlowering.PETAL_DRY, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(FLOWER, 3), register);
		registerAspect(new ItemStack(ModuleFlowering.PETAL_SALTY, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1).add(Aspect.CRAFT, 1), register);
		registerAspect(new ItemStack(ModuleFlowering.PETAL_SUGARED, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(FLOWER, 3).add(Aspect.PLANT, 1).add(Aspect.LIFE, 1), register);
		for (Block stack : ModuleFlowering.STACK_FLOWER)
		{
			registerAspect(new ItemStack(stack, 1, 3), new AspectList().add(FLOWER, 10), register);
		}
		registerAspect(ModuleFlowering.STACK_DEAD, new AspectList().add(Aspect.DEATH, 5).add(Aspect.PLANT, 1), register);
		// Hemp
		registerAspect(ModuleCrops.SEED_HEMP, new AspectList().add(Aspect.PLANT, 5), register);
		registerAspect(ModuleCrops.HEMP_YARN, new AspectList().add(Aspect.PLANT, 3).add(Aspect.CRAFT, 1), register);
		// Thaum
		registerAspect(ModuleMaterials.INGOT_TWINKLE, new AspectList(new ItemStack(Items.IRON_INGOT)).add(FAIRY, 5), register);
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
		registerCrucibeRecipe("ingot_twinkle", "INGOT_TWINKLE", new ItemStack(ModuleMaterials.INGOT_TWINKLE), new ItemStack(Items.IRON_INGOT), new AspectList().add(FAIRY, 5).add(Aspect.MAGIC, 5));
	}

	private void registerFakeRecipes()
	{
		/**
		 * for (IRecipe recipe : GameRegistry.findRegistry(IRecipe.class).getValues()) {
		 * Item output = recipe.getRecipeOutput().getItem(); if (output ==
		 * ModuleMaterials.NUGGET_TWINKLE || output ==
		 * Item.getItemFromBlock(ModuleMaterials.BLOCK_TWINKLE)) {
		 * ThaumcraftApi.addFakeCraftingRecipe(RegisterHelper.getResourceLocation(output.getUnlocalizedName().substring(5)
		 * + "_fake"), recipe); } }
		 **/
	}

	private void registerResearches()
	{
		ResearchCategories.registerCategory("FLORALIA", null, new AspectList(), RegisterHelper.getResourceLocation("textures/items/cut_flower_rose.png"), RegisterHelper.getResourceLocation("textures/gui/gui_floralia_back_1.jpg"), new ResourceLocation(Thaumcraft.MODID, "textures/gui/gui_research_back_over.png"));
		ThaumcraftApi.registerResearchLocation(RegisterHelper.getResourceLocation("research/floralia"));
	}

	private static void registerAspect(Item item, AspectList aspects, AspectEventProxy register)
	{
		registerAspect(new ItemStack(item), aspects, register);
	}

	private static void registerAspect(Block block, AspectList aspects, AspectEventProxy register)
	{
		registerAspect(new ItemStack(block), aspects, register);
	}

	private static void registerAspect(ItemStack itemstack, AspectList aspects, AspectEventProxy register)
	{
		register.registerObjectTag(itemstack, aspects);
	}

	private static void registerAspect(String oreDict, AspectList aspects, AspectEventProxy register)
	{
		register.registerObjectTag(oreDict, aspects);
	}

	private static void registerAddAspect(Item item, AspectList aspects, AspectEventProxy register)
	{
		registerAddAspect(new ItemStack(item), aspects, register);
	}

	private static void registerAddAspect(Block block, AspectList aspects, AspectEventProxy register)
	{
		registerAddAspect(new ItemStack(block), aspects, register);
	}

	private static void registerAddAspect(ItemStack itemstack, AspectList aspects, AspectEventProxy register)
	{
		register.registerComplexObjectTag(itemstack, aspects);
	}

	private static void registerAddAspect(String oreDict, AspectList aspects, AspectEventProxy register)
	{
		register.registerComplexObjectTag(oreDict, aspects);
	}

	private void registerCrucibeRecipe(String path, String key, ItemStack result, ItemStack recipe, AspectList aspects)
	{
		ThaumcraftApi.addCrucibleRecipe(RegisterHelper.getResourceLocation(path), new CrucibleRecipe(key, result, recipe, aspects));
	}
}
