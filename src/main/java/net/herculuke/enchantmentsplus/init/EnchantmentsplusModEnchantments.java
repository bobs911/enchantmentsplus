
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.herculuke.enchantmentsplus.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import net.herculuke.enchantmentsplus.enchantment.VampiricStrikeEnchantment;
import net.herculuke.enchantmentsplus.enchantment.SwiftDodgeEnchantment;
import net.herculuke.enchantmentsplus.enchantment.PhantomStrikeEnchantment;
import net.herculuke.enchantmentsplus.enchantment.LeapEnchantment;
import net.herculuke.enchantmentsplus.enchantment.EnderStepEnchantment;
import net.herculuke.enchantmentsplus.enchantment.DashEnchantment;
import net.herculuke.enchantmentsplus.enchantment.CrippleEnchantment;
import net.herculuke.enchantmentsplus.enchantment.CloakEnchantment;
import net.herculuke.enchantmentsplus.enchantment.BlitzEnchantment;
import net.herculuke.enchantmentsplus.enchantment.AutoSmeltEnchantment;
import net.herculuke.enchantmentsplus.EnchantmentsplusMod;

public class EnchantmentsplusModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EnchantmentsplusMod.MODID);
	public static final RegistryObject<Enchantment> SWIFT_DODGE = REGISTRY.register("swift_dodge", () -> new SwiftDodgeEnchantment());
	public static final RegistryObject<Enchantment> VAMPIRIC_STRIKE = REGISTRY.register("vampiric_strike", () -> new VampiricStrikeEnchantment());
	public static final RegistryObject<Enchantment> DASH = REGISTRY.register("dash", () -> new DashEnchantment());
	public static final RegistryObject<Enchantment> ENDER_STEP = REGISTRY.register("ender_step", () -> new EnderStepEnchantment());
	public static final RegistryObject<Enchantment> AUTO_SMELT = REGISTRY.register("auto_smelt", () -> new AutoSmeltEnchantment());
	public static final RegistryObject<Enchantment> CRIPPLE = REGISTRY.register("cripple", () -> new CrippleEnchantment());
	public static final RegistryObject<Enchantment> PHANTOM_STRIKE = REGISTRY.register("phantom_strike", () -> new PhantomStrikeEnchantment());
	public static final RegistryObject<Enchantment> CLOAK = REGISTRY.register("cloak", () -> new CloakEnchantment());
	public static final RegistryObject<Enchantment> LEAP = REGISTRY.register("leap", () -> new LeapEnchantment());
	public static final RegistryObject<Enchantment> BLITZ = REGISTRY.register("blitz", () -> new BlitzEnchantment());
}
