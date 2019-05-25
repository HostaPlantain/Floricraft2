package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.item.ItemBasic;
import com.hosta.Floricraft2.item.ItemBasicMeta;
import com.hosta.Floricraft2.item.ItemVial;
import com.hosta.Floricraft2.item.tool.ToolSachet;
import com.hosta.Floricraft2.potion.EffectActive;
import com.hosta.Floricraft2.potion.EffectAntiMob;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleFragrances implements IModule {

	public static final String[]	ANTI_MOBS		= new String[] { "zombie", "skeleton", "creeper", "spider", "ender" };

	// Potion
	public static final Potion		POTION_FLORIC	= new EffectActive("effect.floric", ModuleOthers.COLOR_FLORIC, false).setIconIndex(0, 0);
	public static final Potion		POTION_HILLSTEP	= new EffectActive("effect.hillstep", 0xEEFFDA, false).setIconIndex(0, 0);
	public static final Potion[]	POTION_ANTIS	= new Potion[ANTI_MOBS.length];
	static
	{
		int i = 0;
		Class[][] antiClass = new Class[POTION_ANTIS.length][];
		antiClass[i++] = new Class[] { EntityZombie.class, EntityZombieVillager.class };
		antiClass[i++] = new Class[] { EntitySkeleton.class, EntityWitherSkeleton.class };
		antiClass[i++] = new Class[] { EntityCreeper.class };
		antiClass[i++] = new Class[] { EntitySpider.class, EntityCaveSpider.class };
		antiClass[i++] = new Class[] { EntityEnderman.class };
		String effectAnti = "effect.anti_";
		for (int j = 0; j < i; j++)
		{
			POTION_ANTIS[j] = new EffectAntiMob(effectAnti + ANTI_MOBS[j], 0xADDAAD, antiClass[j]).setIconIndex(0, 0);
		}
	}
	public static final Potion	POTION_NO_TARGET	= new EffectActive("effect.no_target", 0xFFDAFF, true).setIconIndex(0, 0);

	// Vial
	public static final Item	VIAL_EMPTY			= new ItemVial("vial_empty");
	public static final Item	VIAL_WATER			= new ItemBasic("vial_water").setMaxStackSize(1);
	public static final Item	VIAL_FLOWER			= new ItemBasic("vial_flower").setMaxStackSize(1);
	public static final Item	VIAL_ANTIS			= new ItemBasicMeta("vial_anti", ANTI_MOBS).setMaxStackSize(1);
	public static final Item	VIAL_MOON			= new ItemBasic("vial_moon");

	// Sachet
	public static final Item	SACHET_SAC			= new ItemBasic("sachet_sac");
	public static final Item	SACHET_FLOWER		= new ToolSachet("sachet_flower", (Potion) null);
	// public static final Item SACHET_ENDEARING;
	public static final Item[]	SACHET_ANTIS		= new Item[ANTI_MOBS.length];
	static
	{
		String sachetAnti = "sachet_anti_";
		for (int i = 0; i < SACHET_ANTIS.length; i++)
		{
			SACHET_ANTIS[i] = new ToolSachet(sachetAnti + ANTI_MOBS[i], POTION_ANTIS[i]);
		}
	}

	// Potpourri
	// public static final Block POTPOURRI;

	@Override
	public void registerItems()
	{
		register(VIAL_EMPTY, VIAL_WATER, VIAL_FLOWER, VIAL_ANTIS, VIAL_MOON, SACHET_SAC, SACHET_FLOWER);
		register(SACHET_ANTIS);
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
				shapedRecipe(null, SACHET_SAC, "ttt", "c c", " c ", 't', ModuleCrops.HEMP_TWINE, 'c', ModuleCrops.HEMP_CLOTH),
				shapedRecipe(null, new ItemStack(SACHET_FLOWER, 1, 0), "ppp", "ppp", "tst", 'p', "petalsDry", 's', SACHET_SAC, 't', ModuleCrops.HEMP_TWINE),
				shapelessRecipe(null, new ItemStack(SACHET_FLOWER, 1, 0), new ItemStack(SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), "petalsDry")
		);

		register
		(
				brewingRecipe(new ItemStack(VIAL_WATER), new ItemStack(ModuleFlower.PETALS_RAW, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(VIAL_FLOWER))
		);

		Item[] anti = new Item[] { Items.ROTTEN_FLESH, Items.BONE, Items.GUNPOWDER, Items.SPIDER_EYE, Items.ENDER_PEARL };
		for (int i = 0; i < anti.length; i++)
		{
			register(
					shapedRecipe("sachet_anti", new ItemStack(SACHET_ANTIS[i], 1, 0), "ppp", "ppp", "tst", 'p', anti[i], 's', new ItemStack(SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), 't', ModuleCrops.HEMP_TWINE),
					shapelessRecipe(null, new ItemStack(SACHET_ANTIS[i], 1, 0), new ItemStack(SACHET_FLOWER, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(VIAL_ANTIS, 1, i)),
					shapelessRecipe(null, new ItemStack(SACHET_ANTIS[i], 1, 0), new ItemStack(SACHET_ANTIS[i], 1, OreDictionary.WILDCARD_VALUE), "petalsDry")
			);
		}
	}
}
