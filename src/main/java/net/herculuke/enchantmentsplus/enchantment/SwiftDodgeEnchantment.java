
package net.herculuke.enchantmentsplus.enchantment;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EquipmentSlot;

public class SwiftDodgeEnchantment extends Enchantment {
	public SwiftDodgeEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_LEGS, slots);
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
