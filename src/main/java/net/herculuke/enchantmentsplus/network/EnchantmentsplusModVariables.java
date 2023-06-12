package net.herculuke.enchantmentsplus.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import net.herculuke.enchantmentsplus.EnchantmentsplusMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantmentsplusModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		EnchantmentsplusMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level.isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level.isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level.isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			clone.abilitycooldown = original.abilitycooldown;
			clone.abilityselection = original.abilityselection;
			clone.iscrippled = original.iscrippled;
			clone.cripplelocationx = original.cripplelocationx;
			clone.cripplelocationy = original.cripplelocationy;
			clone.cripplelocationz = original.cripplelocationz;
			clone.boots = original.boots;
			clone.leggings = original.leggings;
			clone.chestplate = original.chestplate;
			clone.helmet = original.helmet;
			clone.blocktobreak = original.blocktobreak;
			if (!event.isWasDeath()) {
			}
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("enchantmentsplus", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public double abilitycooldown = 0;
		public double abilityselection = 0;
		public boolean iscrippled = false;
		public double cripplelocationx = 0;
		public double cripplelocationy = 0;
		public double cripplelocationz = 0;
		public ItemStack boots = ItemStack.EMPTY;
		public ItemStack leggings = ItemStack.EMPTY;
		public ItemStack chestplate = ItemStack.EMPTY;
		public ItemStack helmet = ItemStack.EMPTY;
		public double blocktobreak = 0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				EnchantmentsplusMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("abilitycooldown", abilitycooldown);
			nbt.putDouble("abilityselection", abilityselection);
			nbt.putBoolean("iscrippled", iscrippled);
			nbt.putDouble("cripplelocationx", cripplelocationx);
			nbt.putDouble("cripplelocationy", cripplelocationy);
			nbt.putDouble("cripplelocationz", cripplelocationz);
			nbt.put("boots", boots.save(new CompoundTag()));
			nbt.put("leggings", leggings.save(new CompoundTag()));
			nbt.put("chestplate", chestplate.save(new CompoundTag()));
			nbt.put("helmet", helmet.save(new CompoundTag()));
			nbt.putDouble("blocktobreak", blocktobreak);
			return nbt;
		}

		public void readNBT(Tag Tag) {
			CompoundTag nbt = (CompoundTag) Tag;
			abilitycooldown = nbt.getDouble("abilitycooldown");
			abilityselection = nbt.getDouble("abilityselection");
			iscrippled = nbt.getBoolean("iscrippled");
			cripplelocationx = nbt.getDouble("cripplelocationx");
			cripplelocationy = nbt.getDouble("cripplelocationy");
			cripplelocationz = nbt.getDouble("cripplelocationz");
			boots = ItemStack.of(nbt.getCompound("boots"));
			leggings = ItemStack.of(nbt.getCompound("leggings"));
			chestplate = ItemStack.of(nbt.getCompound("chestplate"));
			helmet = ItemStack.of(nbt.getCompound("helmet"));
			blocktobreak = nbt.getDouble("blocktobreak");
		}
	}

	public static class PlayerVariablesSyncMessage {
		public PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
					variables.abilitycooldown = message.data.abilitycooldown;
					variables.abilityselection = message.data.abilityselection;
					variables.iscrippled = message.data.iscrippled;
					variables.cripplelocationx = message.data.cripplelocationx;
					variables.cripplelocationy = message.data.cripplelocationy;
					variables.cripplelocationz = message.data.cripplelocationz;
					variables.boots = message.data.boots;
					variables.leggings = message.data.leggings;
					variables.chestplate = message.data.chestplate;
					variables.helmet = message.data.helmet;
					variables.blocktobreak = message.data.blocktobreak;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
