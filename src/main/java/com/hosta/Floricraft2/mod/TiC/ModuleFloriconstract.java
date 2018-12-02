package com.hosta.Floricraft2.mod.TiC;

import java.util.ArrayList;
import java.util.List;

import com.hosta.Floricraft2.block.BlockBasicFluid;
import com.hosta.Floricraft2.mod.TiC.client.RenderThrowingRose;
import com.hosta.Floricraft2.mod.TiC.modifier.ModifierFloric;
import com.hosta.Floricraft2.mod.TiC.modifier.TraitTwinkle;
import com.hosta.Floricraft2.mod.TiC.ranged.EntityThrowingRose;
import com.hosta.Floricraft2.mod.TiC.ranged.ThrowingRose;
import com.hosta.Floricraft2.module.Module;
import com.hosta.Floricraft2.module.ModuleItems;
import com.hosta.Floricraft2.module.ModuleOthers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.book.TinkerBook;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.tools.TinkerTools;

@Pulse(id = "Floriconstract", description = "All the Floric Constract in one handy package")
public class ModuleFloriconstract extends Module{

	//Part
	public static ToolPart partPetal;
	//Tool
	public static ToolCore throwingRose;
	//Fluid
	private static final String PATH_MOLTEN = "tconstruct:blocks/fluids/molten_metal";
	public static final Fluid FLUID_TWINKLE = new Fluid("molten_twinkle", new ResourceLocation(PATH_MOLTEN), new ResourceLocation(PATH_MOLTEN + "_flow"), ModuleOthers.COLOR_TWINKLE);
	public static Block moltenTwinkle;
	//Modifier
	public static Modifier modFloric;
	public static Modifier modTwinkle;
	//Material
	public static Material materialTwinkle;
	
	public Module registerPulse()
	{
		TConstruct.pulseManager.registerPulse(this);
		return this;
	}
	
	@Override
	public void preInit() {	}

	@SubscribeEvent
	public void registerBlocks(Register<Block> event)
	{
		this.registerFluids();
		
		List<Block> blocks = new ArrayList<Block>();
		moltenTwinkle = new BlockBasicFluid("molten_twinkle", FLUID_TWINKLE, net.minecraft.block.material.Material.LAVA);
		blocks.add(moltenTwinkle);
		
		registerBlocks(event.getRegistry(), blocks);
	}

	private void registerFluids()
	{
		FluidRegistry.registerFluid(FLUID_TWINKLE);
	}

	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		//Items
		List<Item> items = new ArrayList<Item>();		
		//Parts
		partPetal = new ToolPart(Material.VALUE_Ingot);
		partPetal.setUnlocalizedName("part_petal");
		items.add(partPetal);
		//Tools
		throwingRose = new ThrowingRose();
		throwingRose.setUnlocalizedName("throwing_rose");
		items.add(throwingRose);		
		registerItems(event.getRegistry(), items);
		
		//Stencil for parts
		ItemStack stencil = new ItemStack(TinkerTools.pattern);
        Pattern.setTagForPart(stencil, partPetal);
        TinkerRegistry.registerStencilTableCrafting(stencil);

        //Modifiers
        this.registerModifiers();
        
        //Materials
        this.registerMaterials();
	}
	
	private void registerModifiers()
	{
		modFloric = new ModifierFloric("floric", ModuleOthers.COLOR_FLORIC, 3, 72);
		modFloric.addItem(new ItemStack(ModuleItems.PETAL_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 1);
		modFloric.addItem(new ItemStack(ModuleItems.PETALS_RAW, 1, OreDictionary.WILDCARD_VALUE), 1, 9);
		
		modTwinkle = new TraitTwinkle("twinkle", ModuleOthers.COLOR_TWINKLE);
	}
	
	private void registerMaterials()
	{
		MaterialIntegration materialIn;
		//Twinkle is soft, light, weak but twinkling material.
		materialTwinkle = new Material("twinkle", ModuleOthers.COLOR_TWINKLE);
		TinkerRegistry.addMaterialTrait(materialTwinkle, (ITrait)modTwinkle, modTwinkle.getIdentifier());
		TinkerRegistry.addMaterialStats(materialTwinkle,	new HeadMaterialStats(300, 6.5f, 3.5f, 0),	new HandleMaterialStats(1.2f, -100),
															new ExtraMaterialStats(250),				new BowMaterialStats(1.5f, 0.5f, -3));
		materialIn = new MaterialIntegration(materialTwinkle, FLUID_TWINKLE, "Twinkle");
		materialIn.preInit();
		TinkerRegistry.integrate(materialIn);
	}
	
	@Override
	public List<IRecipe> registerRecipes()
	{
		for (Block stack : ModuleItems.STACK_FLOWER)
		{
			registerDryingRecipes(new ItemStack(stack, 1, 0), new ItemStack(stack, 1, 3), 300);
		}
		TinkerRegistry.registerToolForgeCrafting(throwingRose);
		
		return null;
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
		//Items
		ModelRegisterUtil.registerPartModel(partPetal);
		ModelRegisterUtil.registerToolModel(throwingRose);
		
		//blocks
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(moltenTwinkle);
		registerItemBlockRenders(blocks);
		
		//Entity
	    RenderingRegistry.registerEntityRenderingHandler(EntityThrowingRose.class, RenderThrowingRose::new);

	    //Modifier
	    for (IModifier mod : TinkerRegistry.getAllModifiers())
	    {
	    	ModelRegisterUtil.registerModifierModel(mod, getResourceLocation("models/item/modifiers/" + mod.getIdentifier()));
	    }
	    
	    //GUI Tool Forge
	    this.registerToolBuildInfo();

	    //Page for the Book
	    TinkerBook.INSTANCE.addRepository(new FileRepository("floricraft2:book"));
	}

	@SideOnly(Side.CLIENT)
	private void registerToolBuildInfo()
	{
		//Throwing Rose
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
