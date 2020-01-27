package com.hosta.Floricraft2.mod.Botania;

import com.hosta.Floricraft2.mod.Botania.item.ItemSwordIzanagi;
import com.hosta.Floricraft2.module.IModule;

import net.minecraft.item.Item;

public class ModuleBotania implements IModule {

	public static final Item SWORD_IZANAGI = new ItemSwordIzanagi("sword_izanagi");

	@Override
	public void registerItems()
	{
		register(SWORD_IZANAGI);
	}

	@Override
	public void registerEntities()
	{
		//Registries.registerEntity(EntityProjectileIzanagi.class, "projectile_izanagi", ModuleOthers.PROJECTILE_IZANAGI);
	}

	@Override
	public void registerModels()
	{
		
	}
}
