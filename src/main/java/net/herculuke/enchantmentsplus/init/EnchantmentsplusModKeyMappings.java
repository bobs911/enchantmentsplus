
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.herculuke.enchantmentsplus.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.herculuke.enchantmentsplus.network.UseEnchantAbilityMessage;
import net.herculuke.enchantmentsplus.network.RotateEnchantAbilityMessage;
import net.herculuke.enchantmentsplus.EnchantmentsplusMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class EnchantmentsplusModKeyMappings {
	public static final KeyMapping ROTATE_ENCHANT_ABILITY = new KeyMapping("key.enchantmentsplus.rotate_enchant_ability", GLFW.GLFW_KEY_Z, "key.categories.multiplayer");
	public static final KeyMapping USE_ENCHANT_ABILITY = new KeyMapping("key.enchantmentsplus.use_enchant_ability", GLFW.GLFW_KEY_X, "key.categories.multiplayer");

	@SubscribeEvent
	public static void registerKeyBindings(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(ROTATE_ENCHANT_ABILITY);
		ClientRegistry.registerKeyBinding(USE_ENCHANT_ABILITY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event) {
			if (Minecraft.getInstance().screen == null) {
				if (event.getKey() == ROTATE_ENCHANT_ABILITY.getKey().getValue()) {
					if (event.getAction() == GLFW.GLFW_PRESS) {
						EnchantmentsplusMod.PACKET_HANDLER.sendToServer(new RotateEnchantAbilityMessage(0, 0));
						RotateEnchantAbilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
					}
				}
				if (event.getKey() == USE_ENCHANT_ABILITY.getKey().getValue()) {
					if (event.getAction() == GLFW.GLFW_PRESS) {
						EnchantmentsplusMod.PACKET_HANDLER.sendToServer(new UseEnchantAbilityMessage(0, 0));
						UseEnchantAbilityMessage.pressAction(Minecraft.getInstance().player, 0, 0);
					}
				}
			}
		}
	}
}
