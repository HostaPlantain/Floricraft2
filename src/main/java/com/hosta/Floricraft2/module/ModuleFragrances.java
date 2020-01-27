package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Floricraft2;
import com.hosta.Floricraft2.item.ItemBase;
import com.hosta.Floricraft2.item.fragrance.ItemSachet;
import com.hosta.Floricraft2.item.fragrance.ItemVial;
import com.hosta.Floricraft2.item.fragrance.ItemVialEssence;
import com.hosta.Floricraft2.potion.EffectActive;
import com.hosta.Floricraft2.potion.EffectAntiMob;
import com.hosta.Floricraft2.util.Helper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleFragrances implements IModule, IModuleRecipe {

	// Potion
	public static final Potion			POTION_FLORIC	= new EffectActive("effect.floric", ModuleOthers.COLOR_FLORIC, false).setIconIndex(0, 0);
	public static final Potion			POTION_HILLSTEP	= new EffectActive("effect.hillstep", 0xEEFFDA, false).setIconIndex(0, 0);
	public static final EffectAntiMob[]	POTION_ANTIS	= new EffectAntiMob[Floricraft2.CONFIG.getAddedAntiPotions().length];
	static
	{
		int i = 0;
		for (String anti : Floricraft2.CONFIG.getAddedAntiPotions())
		{
			POTION_ANTIS[i++] = getAntiEffect(anti);
		}
	}
	
	private static EffectAntiMob getAntiEffect(String anti)
	{
		String[] config = anti.split(";");
		Class[] antiClass = new Class[config.length - 3];
		for (int j = 0; j < antiClass.length; j++)
		{
			try
			{
				antiClass[j] = Class.forName(config[3 + j]);
			}
			catch (ClassNotFoundException e) {	}
		}
		return (EffectAntiMob)new EffectAntiMob("effect.anti_" + config[0], Integer.parseInt(config[1]), antiClass, config[2]).setIconIndex(0, 0);
	}
		
	public static final Potion	POTION_NO_TARGET	= new EffectActive("effect.no_target", ModuleOthers.COLOR_FLORIC, true).setIconIndex(0, 0);

	// Vial
	public static final Item	VIAL_EMPTY			= new ItemVial("vial_empty");
	public static final Item	VIAL_WATER			= new ItemBase("vial_water").setMaxStackSize(1);
	public static final Item	VIAL_FLOWER			= new ItemVialEssence("vial_flower").setMaxStackSize(1);
	public static final Item	VIAL_MOON			= new ItemBase("vial_moon");
	public static final Item	VIAL_POTION			= new ItemVialEssence("vial_potion").setMaxStackSize(1);
	public static final Item	VIAL_MIX			= new ItemVialEssence("vial_mix").setMaxStackSize(1);

	// Sachet
	public static final Item	SACHET_SAC			= new ItemBase("sachet_sac");
	public static final Item	SACHET_FLOWER		= new ItemSachet("sachet_flower");
	// public static final Item SACHET_ENDEARING;

	// Potpourri
	// public static final Block POTPOURRI;

	@Override
	public void registerItems()
	{
		register(VIAL_EMPTY, VIAL_WATER, VIAL_FLOWER, VIAL_MOON, VIAL_POTION, VIAL_MIX, SACHET_SAC, SACHET_FLOWER);
	}

	@Override
	public void registerOreDictionary()
	{
		OreDictionary.registerOre("vialBase", VIAL_FLOWER);
		OreDictionary.registerOre("vialBase", VIAL_POTION);
		OreDictionary.registerOre("vialEffective", VIAL_FLOWER);
		OreDictionary.registerOre("vialEffective", VIAL_POTION);
		OreDictionary.registerOre("vialEffective", VIAL_MIX);
	}

	@Override
	public void registerPotions()
	{
		register(POTION_FLORIC, POTION_HILLSTEP);
		register(POTION_ANTIS);
		register(POTION_NO_TARGET);
	}

	@Override
	public void registerRecipes()
	{
		register
		(
			shapedRecipe(null, SACHET_SAC, "tpt", "cpc", " c ", 'p', "petalsDry", 't', ModuleCrops.HEMP_TWINE, 'c', ModuleCrops.HEMP_CLOTH),
			shapedRecipe(null, Helper.setEffect(new ItemStack(SACHET_FLOWER, 1, 0), new PotionEffect(POTION_FLORIC)), "ccc", "ccc", "tst", 'c', "petalsDry", 't', ModuleCrops.HEMP_TWINE, 's', SACHET_SAC),
			shapedRecipe(null, new ItemStack(VIAL_EMPTY, 3), "b b", " b ", 'b', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.BROWN.getMetadata()))
		);
		register
		(
			effectRecipe(null, SACHET_FLOWER, "b", "a", 'a', SACHET_SAC, 'b', "vialEffective"),
			effectRecipe(null, SACHET_FLOWER, "b", "a", 'a', SACHET_SAC, 'b', VIAL_MOON),
			effectRecipe(null, VIAL_POTION, "b", "a", 'a', VIAL_MOON, 'b', Items.POTIONITEM),
			effectRecipe(null, VIAL_MIX, "b", "a", 'a', VIAL_MOON, 'b', "vialBase"),
			effectRecipe(null, VIAL_MIX, "b", "a", 'a', VIAL_MIX, 'b', "vialEffective")
		);

		ItemStack vialFlower = Helper.setEffect(new ItemStack(VIAL_FLOWER), new PotionEffect(POTION_FLORIC, 400, 0));
		register(brewingRecipe(new ItemStack(VIAL_WATER), new ItemStack(ModuleFlowers.PETALS_RAW, 1, OreDictionary.WILDCARD_VALUE), vialFlower));

		for (EffectAntiMob anti : POTION_ANTIS)
		{
			register(brewingRecipe(vialFlower, anti.getItemToCraft(), Helper.setEffect(new ItemStack(VIAL_FLOWER), new PotionEffect(anti, 400, 0))));
			register(shapedRecipe(null, Helper.setEffect(new ItemStack(SACHET_FLOWER, 1, 0), new PotionEffect(anti)), "ccc", "ccc", "tst", 'c', anti.getItemToCraft(), 's', SACHET_SAC, 't', ModuleCrops.HEMP_TWINE));
		}
	}
}
