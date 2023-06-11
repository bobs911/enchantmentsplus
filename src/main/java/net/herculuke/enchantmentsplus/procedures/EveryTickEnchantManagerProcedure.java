package net.herculuke.enchantmentsplus.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

import net.herculuke.enchantmentsplus.network.EnchantmentsplusModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EveryTickEnchantManagerProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).iscrippled == true) {
			{
				Entity _ent = entity;
				_ent.teleportTo(((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationx),
						((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationy),
						((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationz));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationx),
							((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationy),
							((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).cripplelocationz), _ent.getYRot(), _ent.getXRot());
			}
		}
	}
}
