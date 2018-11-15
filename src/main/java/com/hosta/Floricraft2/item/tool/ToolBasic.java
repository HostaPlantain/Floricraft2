package com.hosta.Floricraft2.item.tool;

import com.hosta.Floricraft2.item.ItemBasic;

public class ToolBasic extends ItemBasic {
	
	public ToolBasic(String name, int maxDamageIn)
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
