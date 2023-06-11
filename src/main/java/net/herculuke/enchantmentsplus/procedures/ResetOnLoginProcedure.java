package net.herculuke.enchantmentsplus.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.herculuke.enchantmentsplus.network.EnchantmentsplusModVariables;
import net.herculuke.enchantmentsplus.EnchantmentsplusMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ResetOnLoginProcedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		execute(event, event.getEntity().level, event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		EnchantmentsplusMod.queueServerWork(200, () -> {
			{
				double _setval = 0;
				entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.abilitycooldown = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		});
	}
}
