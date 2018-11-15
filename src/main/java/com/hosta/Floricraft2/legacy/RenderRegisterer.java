package com.hosta.Floricraft2.legacy;

import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.item.IMetaName;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRegisterer {

	//Register Render
	public static void register(Item item)
	{
		RenderRegisterer.registerRender(item, 0, item.getUnlocalizedName().substring(5));
	}	
	
	public static void register(Item item, int meta, String fileName)
	{
		RenderRegisterer.registerRender(item, meta, fileName);
	}	
	
	public static void register(Block block)
	{
		RenderRegisterer.register(Item.getItemFromBlock(block));
	}

	public static void registerWithMeta(Block block)
	{
		for(int i = 0; i < 16 ; i++)
		{
			RenderRegisterer.register(block, i, block.getUnlocalizedName().substring(5) + "_" + ((IMetaName)block).getSubName(i));
		}
	}

	public static void register(Block block, int meta, String fileName)
	{
		RenderRegisterer.registerRender(Item.getItemFromBlock(block), meta, fileName);
	}
	
	private static void registerRender(Item item, int meta, String fileName)
	{
		//Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + fileName, "inventory"));
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + fileName, "inventory"));
	}
	
	public static void registerLeaves(Block block)
	{
		ColorRegisterer.registerLeaves(block);
	}

	public static void registerGrass(Block block)
	{
		ColorRegisterer.registerGrass(block);
	}
	
	public static <T extends TileEntity> void registerRender(Class<T> tileEntity, TileEntitySpecialRenderer<T> renderer)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntity, renderer);
	}
}
