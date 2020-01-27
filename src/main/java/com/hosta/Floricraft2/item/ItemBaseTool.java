package com.hosta.Floricraft2.item;

public class ItemBaseTool extends ItemBase {

	public ItemBaseTool(String name, int maxDamageIn)
	{
		super(name);
		this.setMaxStackSize(1).setMaxDamage(maxDamageIn);
	}

	@Override
	public boolean isDamageable()
	{
		return true;
	}
}
