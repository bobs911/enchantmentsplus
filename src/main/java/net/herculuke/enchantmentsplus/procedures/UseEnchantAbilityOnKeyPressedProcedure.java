package net.herculuke.enchantmentsplus.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.herculuke.enchantmentsplus.network.EnchantmentsplusModVariables;
import net.herculuke.enchantmentsplus.init.EnchantmentsplusModEnchantments;

import java.util.Comparator;

public class UseEnchantAbilityOnKeyPressedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double oldlookx = 0;
		double oldlooky = 0;
		double oldlookz = 0;
		if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 0) {
			if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsplusModEnchantments.PHANTOM_STRIKE.get(), (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)) != 0) {
				if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilitycooldown == 0) {
					oldlookx = entity.getX();
					oldlooky = entity.getY();
					oldlookz = entity.getZ();
					if ((entity.getDirection()) == Direction.WEST) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX() - 3), (entity.getY()), (entity.getZ()));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX() - 3), (entity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.EAST) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX() + 3), (entity.getY()), (entity.getZ()));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX() + 3), (entity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.NORTH) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX()), (entity.getY()), (entity.getZ() - 3));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX()), (entity.getY()), (entity.getZ() - 3), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.SOUTH) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX()), (entity.getY()), (entity.getZ() + 3));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX()), (entity.getY()), (entity.getZ() + 3), _ent.getYRot(), _ent.getXRot());
						}
					}
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(oldlookx, oldlooky, oldlookz));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, (entity.getX()), (entity.getY()), (entity.getZ()), 500, 1, 1, 1, 0);
					{
						double _setval = 1;
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.abilitycooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					new Object() {
						private int ticks = 0;
						private float waitTicks;
						private LevelAccessor world;

						public void start(LevelAccessor world, int waitTicks) {
							this.waitTicks = waitTicks;
							MinecraftForge.EVENT_BUS.register(this);
							this.world = world;
						}

						@SubscribeEvent
						public void tick(TickEvent.ServerTickEvent event) {
							if (event.phase == TickEvent.Phase.END) {
								this.ticks += 1;
								if (this.ticks >= this.waitTicks)
									run();
							}
						}

						private void run() {
							{
								double _setval = 0;
								entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.abilitycooldown = _setval;
									capability.syncPlayerVariables(entity);
								});
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, 200);
				}
			}
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 1) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("Coming Soon"), false);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 2) {
			if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsplusModEnchantments.LEAP.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
				if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilitycooldown == 0) {
					if ((entity.getDirection()) == Direction.WEST) {
						entity.setDeltaMovement(new Vec3((-2), 2, 0));
					}
					if ((entity.getDirection()) == Direction.EAST) {
						entity.setDeltaMovement(new Vec3(2, 2, 0));
					}
					if ((entity.getDirection()) == Direction.NORTH) {
						entity.setDeltaMovement(new Vec3(0, 2, (-2)));
					}
					if ((entity.getDirection()) == Direction.SOUTH) {
						entity.setDeltaMovement(new Vec3(0, 2, 2));
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.place")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.place")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ITEM_SLIME, (entity.getX()), (entity.getY()), (entity.getZ()), 300, 1, 1, 1, 0);
					{
						double _setval = 1;
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.abilitycooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					new Object() {
						private int ticks = 0;
						private float waitTicks;
						private LevelAccessor world;

						public void start(LevelAccessor world, int waitTicks) {
							this.waitTicks = waitTicks;
							MinecraftForge.EVENT_BUS.register(this);
							this.world = world;
						}

						@SubscribeEvent
						public void tick(TickEvent.ServerTickEvent event) {
							if (event.phase == TickEvent.Phase.END) {
								this.ticks += 1;
								if (this.ticks >= this.waitTicks)
									run();
							}
						}

						private void run() {
							{
								double _setval = 0;
								entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.abilitycooldown = _setval;
									capability.syncPlayerVariables(entity);
								});
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, 200);
				}
			}
			if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsplusModEnchantments.DASH.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
				if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilitycooldown == 0) {
					if ((entity.getDirection()) == Direction.WEST) {
						entity.setDeltaMovement(new Vec3((-2), 0, 0));
					}
					if ((entity.getDirection()) == Direction.EAST) {
						entity.setDeltaMovement(new Vec3(2, 0, 0));
					}
					if ((entity.getDirection()) == Direction.NORTH) {
						entity.setDeltaMovement(new Vec3(0, 0, (-2)));
					}
					if ((entity.getDirection()) == Direction.SOUTH) {
						entity.setDeltaMovement(new Vec3(0, 0, 2));
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.composter.fill")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.composter.fill")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.FIREWORK, (entity.getX()), (entity.getY()), (entity.getZ()), 300, 2, 1, 2, 1);
					{
						double _setval = 1;
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.abilitycooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					new Object() {
						private int ticks = 0;
						private float waitTicks;
						private LevelAccessor world;

						public void start(LevelAccessor world, int waitTicks) {
							this.waitTicks = waitTicks;
							MinecraftForge.EVENT_BUS.register(this);
							this.world = world;
						}

						@SubscribeEvent
						public void tick(TickEvent.ServerTickEvent event) {
							if (event.phase == TickEvent.Phase.END) {
								this.ticks += 1;
								if (this.ticks >= this.waitTicks)
									run();
							}
						}

						private void run() {
							{
								double _setval = 0;
								entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.abilitycooldown = _setval;
									capability.syncPlayerVariables(entity);
								});
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, 200);
				}
			}
			if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsplusModEnchantments.ENDER_STEP.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)) != 0) {
				if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilitycooldown == 0) {
					if ((entity.getDirection()) == Direction.WEST) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX() - 5), (entity.getY()), (entity.getZ()));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX() - 5), (entity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.EAST) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX() + 5), (entity.getY()), (entity.getZ()));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX() + 5), (entity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.NORTH) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX()), (entity.getY()), (entity.getZ() - 5));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX()), (entity.getY()), (entity.getZ() - 5), _ent.getYRot(), _ent.getXRot());
						}
					}
					if ((entity.getDirection()) == Direction.SOUTH) {
						{
							Entity _ent = entity;
							_ent.teleportTo((entity.getX()), (entity.getY()), (entity.getZ() + 5));
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport((entity.getX()), (entity.getY()), (entity.getZ() + 5), _ent.getYRot(), _ent.getXRot());
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.teleport")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.teleport")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ENCHANT, (entity.getX()), (entity.getY()), (entity.getZ()), 500, 1, 1, 1, 0);
					{
						double _setval = 1;
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.abilitycooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					new Object() {
						private int ticks = 0;
						private float waitTicks;
						private LevelAccessor world;

						public void start(LevelAccessor world, int waitTicks) {
							this.waitTicks = waitTicks;
							MinecraftForge.EVENT_BUS.register(this);
							this.world = world;
						}

						@SubscribeEvent
						public void tick(TickEvent.ServerTickEvent event) {
							if (event.phase == TickEvent.Phase.END) {
								this.ticks += 1;
								if (this.ticks >= this.waitTicks)
									run();
							}
						}

						private void run() {
							{
								double _setval = 0;
								entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.abilitycooldown = _setval;
									capability.syncPlayerVariables(entity);
								});
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, 200);
				}
			}
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 3) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("Coming Soon"), false);
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 4) {
			if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsplusModEnchantments.CLOAK.get(), (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)) != 0) {
				if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilitycooldown == 0) {
					for (int index0 = 0; index0 < 30; index0++) {
						if (!world.getEntitiesOfClass(Arrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).isEmpty()) {
							if (!((Entity) world.getEntitiesOfClass(Arrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
								}
							}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)).level.isClientSide())
								((Entity) world.getEntitiesOfClass(Arrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).stream().sorted(new Object() {
									Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
										return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
									}
								}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)).discard();
						}
						if (!world.getEntitiesOfClass(SpectralArrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).isEmpty()) {
							if (!((Entity) world.getEntitiesOfClass(SpectralArrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
								}
							}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)).level.isClientSide())
								((Entity) world.getEntitiesOfClass(SpectralArrow.class, AABB.ofSize(new Vec3((entity.getX()), (entity.getY()), (entity.getZ())), 2, 2, 2), e -> true).stream().sorted(new Object() {
									Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
										return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
									}
								}.compareDistOf((entity.getX()), (entity.getY()), (entity.getZ()))).findFirst().orElse(null)).discard();
						}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 1, false, false));
					if (entity instanceof LivingEntity _entity && !_entity.level.isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, (int) ((entity instanceof LivingEntity _livEnt ? _livEnt.getArmorValue() : 0) / 5), false, false));
					{
						ItemStack _setval = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.boots = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						ItemStack _setval = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.leggings = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						ItemStack _setval = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY);
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.chestplate = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						ItemStack _setval = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY);
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.helmet = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(0, new ItemStack(Blocks.AIR));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.FEET, new ItemStack(Blocks.AIR));
						}
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(1, new ItemStack(Blocks.AIR));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Blocks.AIR));
						}
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(Blocks.AIR));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Blocks.AIR));
						}
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(3, new ItemStack(Blocks.AIR));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.AIR));
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.amethyst_cluster.place")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.amethyst_cluster.place")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.ENCHANTED_HIT, (entity.getX()), (entity.getY()), (entity.getZ()), 500, 3, 3, 3, 1);
					{
						double _setval = 1;
						entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.abilitycooldown = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					new Object() {
						private int ticks = 0;
						private float waitTicks;
						private LevelAccessor world;

						public void start(LevelAccessor world, int waitTicks) {
							this.waitTicks = waitTicks;
							MinecraftForge.EVENT_BUS.register(this);
							this.world = world;
						}

						@SubscribeEvent
						public void tick(TickEvent.ServerTickEvent event) {
							if (event.phase == TickEvent.Phase.END) {
								this.ticks += 1;
								if (this.ticks >= this.waitTicks)
									run();
							}
						}

						private void run() {
							{
								double _setval = 0;
								entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
									capability.abilitycooldown = _setval;
									capability.syncPlayerVariables(entity);
								});
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(0, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).boots));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.FEET, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).boots));
								}
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(1, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).leggings));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.LEGS, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).leggings));
								}
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).chestplate));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).chestplate));
								}
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(3, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).helmet));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.HEAD, ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).helmet));
								}
							}
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}.start(world, 200);
				}
			}
		} else if ((entity.getCapability(EnchantmentsplusModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new EnchantmentsplusModVariables.PlayerVariables())).abilityselection == 5) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("Coming Soon"), false);
		}
	}
}
