package net.herculuke.enchantmentsplus.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.herculuke.enchantmentsplus.network.EnchantmentsplusModVariables;

public class RotateEnchantAbilityOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection < 6) {
			{
				double _setval = (entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection + 1;
				entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.abilityselection = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else {
			{
				double _setval = 0;
				entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.abilityselection = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 0) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Main Hand Ability Activated"), true);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 1) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Off Hand Ability Activated"), true);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 2) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Boots Ability Activated"), true);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 3) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Leggings Ability Activated"), true);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 4) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Chestplate Ability Activated"), true);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 5) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(Component.literal("Helmet Ability Activated"), true);
		}
	}
}
