package com.hosta.Floricraft2.mod.TiC;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.mod.TiC.client.RenderThrowingRose;
import com.hosta.Floricraft2.mod.TiC.modifier.ModifierFloric;
import com.hosta.Floricraft2.mod.TiC.ranged.EntityThrowingRose;
import com.hosta.Floricraft2.mod.TiC.ranged.ThrowingRose;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.mantle.pulsar.pulse.Pulse;
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
	
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		partPetal = new ToolPart(Material.VALUE_Ingot);
		partPetal.setUnlocalizedName("part_petal");
		PARTS.add(partPetal);
		PARTS.forEach(part -> ModuleFloriconstract.registerToolPart(event.getRegistry(), part));
		
		throwingRose = new ThrowingRose();
		throwingRose.setUnlocalizedName("throwing_rose");
		TOOLS.add(throwingRose);
		TOOLS.forEach(tool -> ModuleFloriconstract.registerItem(event.getRegistry(), tool));
		
		modFloric = new ModifierFloric("floric", 0xFFDAFF, 3, 72);
		modFloric.addItem(new ItemStack(ModuleItems.PETAL_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 1);
		modFloric.addItem(new ItemStack(ModuleItems.PETALS_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 9);
	}
	
	@SubscribeEvent
	public void registerEntities(Register<EntityEntry> event)
	{
		Module.registerEntity(EntityThrowingRose.class, "throwing_rose");
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		ModuleFloriconstract.PARTS.forEach(part -> ModelRegisterUtil.registerPartModel(part));
		ModuleFloriconstract.TOOLS.forEach(tool -> ModelRegisterUtil.registerToolModel(tool));
	    RenderingRegistry.registerEntityRenderingHandler(EntityThrowingRose.class, RenderThrowingRose::new);
	}

	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> event)
	{
		
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

	@Subscribe
	public void postInit(FMLPostInitializationEvent event)
	{
		PARTS.clear();
		TOOLS.clear();
	}
	
	private static void registerToolPart(IForgeRegistry<Item> registry, ToolPart part)
	{
		Module.registerItem(registry, part);
		ItemStack stencil = new ItemStack(TinkerTools.pattern);
        Pattern.setTagForPart(stencil, part);
        TinkerRegistry.registerStencilTableCrafting(stencil);
	}
	
	@SideOnly(Side.CLIENT)
	private void registerToolBuildInfo()
	{
		ToolBuildGuiInfo throwingRoseInfo;
		throwingRoseInfo = new ToolBuildGuiInfo(ModuleFloriconstract.throwingRose);
		throwingRoseInfo.addSlotPosition(33 - 5, 42);
		throwingRoseInfo.addSlotPosition(33 + 10, 42 - 20);
		throwingRoseInfo.addSlotPosition(33 - 10, 42 - 20);
		throwingRoseInfo.addSlotPosition(33 - 10, 42 + 20);
		TinkerRegistryClient.addToolBuilding(throwingRoseInfo);
	}
	
	private static void registerDryingRecipes(ItemStack input, ItemStack output, int sec)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tagCompound.setTag("output", output.writeToNBT(new NBTTagCompound()));
		tagCompound.setInteger("time", sec);
		FMLInterModComms.sendMessage("tconstruct", "addDryingRecipe", tagCompound);
	}
}
