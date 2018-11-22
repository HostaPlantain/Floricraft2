package com.hosta.Floricraft2.mod.TiC;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.mod.TiC.client.RenderThrowingRose;
import com.hosta.Floricraft2.mod.TiC.modifier.ModifierFloric;
import com.hosta.Floricraft2.mod.TiC.ranged.EntityThrowingRose;
import com.hosta.Floricraft2.mod.TiC.ranged.ThrowingRose;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleBlocks;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;

@Pulse(id = "Floriconstract", description = "All the Floric Constract in one handy package")
public class ModuleFloriconstract extends Module{

	//Part
	public static ToolPart partPetal;
	//Tool
	public static ToolCore throwingRose;
	//Modifier
	public static Modifier modFloric;

	public static final List<ToolPart> PARTS = new ArrayList<ToolPart>();
	public static final List<ToolCore> TOOLS = new ArrayList<ToolCore>();
	
	@Override
	public void preInit()
	{
		TConstruct.pulseManager.registerPulse(this);
	}
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		List<Item> items = new ArrayList<Item>();
		
		partPetal = new ToolPart(Material.VALUE_Ingot);
		partPetal.setUnlocalizedName("part_petal");
		PARTS.add(partPetal);

		throwingRose = new ThrowingRose();
		throwingRose.setUnlocalizedName("throwing_rose");
		TOOLS.add(throwingRose);
		
		items.addAll(PARTS);
		items.addAll(TOOLS);
		registerItems(event.getRegistry(), items);

		for (ToolPart part : PARTS)
		{
			ItemStack stencil = new ItemStack(TinkerTools.pattern);
	        Pattern.setTagForPart(stencil, part);
	        TinkerRegistry.registerStencilTableCrafting(stencil);
		}
		
		modFloric = new ModifierFloric("floric", 0xFFDAFF, 3, 72);
		modFloric.addItem(new ItemStack(ModuleItems.PETAL_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 1);
		modFloric.addItem(new ItemStack(ModuleItems.PETALS_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 9);
	}
	
	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> event)
	{
		for (Block stack : ModuleBlocks.STACK_FLOWER)
		{
			registerDryingRecipes(new ItemStack(stack, 1, 0), new ItemStack(stack, 1, 3), 300);
		}
	}
	
	private static void registerDryingRecipes(ItemStack input, ItemStack output, int sec)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tagCompound.setTag("output", output.writeToNBT(new NBTTagCompound()));
		tagCompound.setInteger("time", sec);
		FMLInterModComms.sendMessage("tconstruct", "addDryingRecipe", tagCompound);
	}

	@SubscribeEvent
	public void registerEntities(Register<EntityEntry> event)
	{
		registerEntity(EntityThrowingRose.class, "throwing_rose", ModuleOthers.THROWING_ROSE);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		ModuleFloriconstract.PARTS.forEach(part -> ModelRegisterUtil.registerPartModel(part));
		ModuleFloriconstract.TOOLS.forEach(tool -> ModelRegisterUtil.registerToolModel(tool));
	    RenderingRegistry.registerEntityRenderingHandler(EntityThrowingRose.class, RenderThrowingRose::new);
	}

	@Subscribe
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide().isClient())
		{
			this.registerToolBuildInfo();
		}
		TOOLS.forEach(tool -> TinkerRegistry.registerToolForgeCrafting(tool));
	}

	@SideOnly(Side.CLIENT)
	private void registerToolBuildInfo()
	{
		List<ToolBuildGuiInfo> builds = new ArrayList<ToolBuildGuiInfo>();
		
		//Throwing Rose
		ToolBuildGuiInfo throwingRoseInfo;
		throwingRoseInfo = new ToolBuildGuiInfo(ModuleFloriconstract.throwingRose);
		throwingRoseInfo.addSlotPosition(33 - 5, 42);
		throwingRoseInfo.addSlotPosition(33 + 10, 42 - 20);
		throwingRoseInfo.addSlotPosition(33 - 10, 42 - 20);
		throwingRoseInfo.addSlotPosition(33 - 10, 42 + 20);
		builds.add(throwingRoseInfo);
		
		builds.forEach(buildInfo -> TinkerRegistryClient.addToolBuilding(buildInfo));
	}

	@Override
	public void postInit()
	{
		PARTS.clear();
		TOOLS.clear();
	}
}
