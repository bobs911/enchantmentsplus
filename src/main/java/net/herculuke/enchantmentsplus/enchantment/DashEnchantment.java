
package net.herculuke.enchantmentsplus.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

import net.herculuke.enchantmentsplus.init.EnchantmentsplusModEnchantments;

import java.util.List;

public class DashEnchantment extends Enchantment {
	public DashEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_FEET, slots);
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return this != ench && !List.of(EnchantmentsplusModEnchantments.ENDER_STEP.get(), EnchantmentsplusModEnchantments.LEAP.get()).contains(ench);
	}
}
