package com.hosta.Floricraft2.block;

public enum EnumStage implements IEnum
{
	STAGE0(0, "stage1"), STAGE1(1, "stage2"), STAGE2(2, "stage3"), STAGE3(3, "stage4"),;

	private final int		META;
	private final String	NAME;

	private EnumStage(int meta, String name)
	{
		this.META = meta;
		this.NAME = name;
	}

	@Override
	public int getMeta()
	{
		return META;
	}

	@Override
	public String getName()
	{
		return NAME;
	}

	public static EnumStage getEnumFromMeta(int meta)
	{
		return EnumStage.values()[meta % EnumStage.values().length];
	}
}
