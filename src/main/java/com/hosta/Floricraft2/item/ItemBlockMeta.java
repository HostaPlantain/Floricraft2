package com.hosta.Floricraft2.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemBlock implements IMetaName {

	private final String[] SUB_ID;

	public ItemBlockMeta(Block block)
	{
		super(block);
		if (!(block instanceof IMetaName))
		{
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaName!", block.getUnlocalizedName()));
		}
		this.SUB_ID = ((IMetaName) this.block).getSubNames();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + getSubName(stack.getMetadata());
	}

	@Override
	public String[] getSubNames()
	{
		return SUB_ID;
	}
}
