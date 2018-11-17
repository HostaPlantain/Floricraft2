package com.hosta.Floricraft2.module;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.hosta.Floricraft2.Reference;
import com.hosta.Floricraft2.enchantment.EnchantmentFloric;
import com.hosta.Floricraft2.packet.PacketNBTParticle;
import com.hosta.Floricraft2.packet.PacketNBTParticleHandler;
import com.hosta.Floricraft2.potion.EffectActive;
import com.hosta.Floricraft2.potion.EffectAntiMob;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleOthers extends Module {
	
	public static final String[] ANTI_MOBS = new String[]{"zombie", "skeleton", "creeper", "spider", "ender"};
	
	//Tab
	public static final CreativeTabs TAB_FLORICRAFT = new CreativeTabs("floricraft2")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {return new ItemStack(ModuleItems.CUT_FLOWER, 1, 12);}
    };
	
	//Potion
	public static final Potion POTION_FLORIC = new EffectActive("effect.floric", 0xFFDAFF, false).setIconIndex(0, 0);
	public static final Potion POTION_HILLSTEP = new EffectActive("effect.hillstep", 0xEEFFDA, false).setIconIndex(0, 0);
	public static final Potion[] POTION_ANTIS = new Potion[ANTI_MOBS.length];
	static
	{
		int i = 0;
		Class[][] antiClass = new Class[POTION_ANTIS.length][];
		antiClass[i++] = new Class[]{EntityZombie.class, EntityZombieVillager.class};
		antiClass[i++] = new Class[]{EntitySkeleton.class, EntityWitherSkeleton.class};
		antiClass[i++] = new Class[]{EntityCreeper.class};
		antiClass[i++] = new Class[]{EntitySpider.class, EntityCaveSpider.class};
		antiClass[i++] = new Class[]{EntityEnderman.class};
		String effectAnti = "effect.anti_";
		for (int j = 0; j < i; j++)
		{
			POTION_ANTIS[j] = new EffectAntiMob(effectAnti + ANTI_MOBS[j], 0xADDAAD, antiClass[j]).setIconIndex(0, 0);
		}
	}
	public static final Potion POTION_NO_TARGET = new EffectActive("effect.no_target", 0xFFDAFF, true).setIconIndex(0, 0);
	
	//Enchantment
	public static final Enchantment ENCHANT_FLORIC = new EnchantmentFloric("floric", Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	
	//Biome
	//public static final Biome BIOME_ROSE_LAND;
	//public static final Biome BIOME_TULIP_LAND;
	
	//Network
	public static final SimpleNetworkWrapper NETWORK_PARTICLE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID_SHORT + "." + "part");

	//Entity
	public static int entityId = 0;
	
	@SubscribeEvent
	public void registerPotions(Register<Potion> event)
	{
		List<Potion> potions = new ArrayList<Potion>();
		
		potions.add(POTION_FLORIC);
		potions.add(POTION_HILLSTEP);
		for (Potion potion : POTION_ANTIS)
		{
			potions.add(potion);
		}
		potions.add(POTION_NO_TARGET);
		
		potions.forEach(potion -> Module.registerPotion(event.getRegistry(), potion));
	}
	
	@SubscribeEvent
	public void registerEnchantments(Register<Enchantment> event)
	{
		List<Enchantment> enchantments = new ArrayList<Enchantment>();
		
		enchantments.add(ENCHANT_FLORIC);
		
		enchantments.forEach(enchantment -> Module.registerEnchantment(event.getRegistry(), enchantment));
	}
	
	@Subscribe
	public static void preInit(FMLPreInitializationEvent event)
	{
		NETWORK_PARTICLE.registerMessage(PacketNBTParticleHandler.class, PacketNBTParticle.class, 0, Side.CLIENT);
	}
}
