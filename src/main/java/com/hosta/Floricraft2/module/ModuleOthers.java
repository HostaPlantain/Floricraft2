package com.hosta.Floricraft2.module;

import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.enchantment.EnchantmentFloric;
import com.hosta.Floricraft2.item.crafting.RecipeNaming;
import com.hosta.Floricraft2.packet.PacketNBTParticle;
import com.hosta.Floricraft2.packet.PacketNBTParticleHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleOthers implements IModule {
	
	//Color
	public static final int COLOR_FLORIC = 0xFFDAFF;
	public static final int COLOR_TWINKLE = 0xFFEDFF;

	//Tab
	public static final CreativeTabs TAB_FLORICRAFT = new CreativeTabs("floricraft2")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {return new ItemStack(ModuleFlowering.CUT_FLOWER, 1, 12);}
    };
	
	//Enchantment
	public static final Enchantment ENCHANT_FLORIC = new EnchantmentFloric("floric", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	
	//Biome
	//public static final Biome BIOME_ROSE_LAND;
	//public static final Biome BIOME_TULIP_LAND;
	
	//Network
	public static final SimpleNetworkWrapper NETWORK_PARTICLE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID_SHORT + "." + "part");
		
	//Entity
	public static final int THROWING_ROSE = 0;
	
	//Advancement
	
	@Override
	public void preInit()
	{
		NETWORK_PARTICLE.registerMessage(PacketNBTParticleHandler.class, PacketNBTParticle.class, 0, Side.CLIENT);
	}
	
	@Override
	public void registerEnchantments()
	{
		register(ENCHANT_FLORIC);
	}
	
	@Override
	public void registerRecipes()
	{
		register(new RecipeNaming(null));
	}
}
