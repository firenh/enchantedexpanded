package fireopal.enchantedexpanded.enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import fireopal.enchantedexpanded.duck.DuckPlayerEntity;
import fireopal.enchantedexpanded.gameplay.OnAttack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagnitudeEnchantment extends EEEnchantment {
    private static final int SEGMENTS_IN_BLOCK = 16;
    private static final double BASE_DISTANCE = 3.5;
    private static final double LEVEL_DISTANCE = 1;
    private static final int COOLDOWN_MISS = 20;
    private static final int COOLDOWN_HIT = 60;
    
    protected MagnitudeEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot slotType) {
        super(weight, type, new EquipmentSlot[]{slotType});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return (other != Enchantments.SWEEPING);
    }

    @Override
    public int getMinPower(int level) {
        return 20 + 5 * level;
    }

    @Override
    public int getMaxPower(int level) {
        return 30 + level * 5;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return (float) level / 2f;
    }

    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    public static void onUseMagnitude(LivingEntity entity, ItemStack stack) {
        if (entity instanceof PlayerEntity) {
            if (((DuckPlayerEntity)(PlayerEntity)entity).getMagnitudeCooldown() <=0) {
                doMagnitudeAttack(entity, stack);
            }
        } else {
            doMagnitudeAttack(entity, stack);
        }

        // EnchantedExpanded.LOGGER.info("mAGNITUDE");
    }
    

    public static Vec3d getLookVector(Entity user) {
        float yaw = user.getYaw();
        float pitch = user.getPitch();
        float roll = 0.0f;

        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        float g = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));

        return new Vec3d(f, g, h);
    }

    private static void doMagnitudeAttack(LivingEntity attacker, ItemStack stack) {
        World world = attacker.getWorld();
        Vec3d lookVector = getLookVector(attacker).multiply(1.0 / (double) SEGMENTS_IN_BLOCK);
        // EnchantedExpanded.LOGGER.info("lookVector: " + lookVector);

        ItemStack selectedItem = attacker.getMainHandStack();
        int level = EnchantmentHelper.getLevel(EEEnchantments.MAGNITUDE, attacker.getMainHandStack());

        if (level <= 0) {
            return;
        } else if (!ItemStack.areEqual(stack, selectedItem)) {
            return;
        }

        List<MagnitudeEffectType> magnitudeEffectTypes = new ArrayList<>();

        for (MagnitudeEffectType met : MagnitudeEffectType.values()) {
            if (EnchantmentHelper.getLevel(met.getEnchantment(), attacker.getMainHandStack()) > 1) {
                magnitudeEffectTypes.add(met);
            }
        }

        Function<LivingEntity, DamageSource> damageSourceFunction = attacker instanceof PlayerEntity ? e -> world.getDamageSources().playerAttack((PlayerEntity) attacker) : world.getDamageSources()::mobAttack;
        DamageSource damageSource = damageSourceFunction.apply(attacker); 
        double maxDistance = BASE_DISTANCE + (level - 1) * LEVEL_DISTANCE;

        if (attacker instanceof PlayerEntity) {
            ((DuckPlayerEntity) (PlayerEntity) attacker).setMagnitudeCooldown(COOLDOWN_MISS);
        }

        if (world instanceof ServerWorld) {
            world.playSound(null, attacker.getX(), attacker.getEyeY(), attacker.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.5f, 2f - level * 0.25f);
        }

        for (int i = 0; i < maxDistance * SEGMENTS_IN_BLOCK; i += 1) {
            Vec3d pos = attacker.getEyePos().add(lookVector.multiply(i));
            // EnchantedExpanded.LOGGER.info("magnitude in progress: segment " + i);

            double h = (maxDistance * SEGMENTS_IN_BLOCK) / (double) i;

            if ((Math.floor(h) - h) == 0 || i + 1 == maxDistance * SEGMENTS_IN_BLOCK) spawnMagnitudeParticles(pos, world, magnitudeEffectTypes);

            if (doSingleAttackPortion(world, attacker, pos, i, (int) maxDistance * SEGMENTS_IN_BLOCK, lookVector, damageSource, magnitudeEffectTypes)) {
                if (attacker instanceof PlayerEntity) {
                    ((DuckPlayerEntity) (PlayerEntity) attacker).setMagnitudeCooldown(COOLDOWN_HIT);
                }
                
                break;
            }
        }
    }

    private static boolean doSingleAttackPortion(World world, LivingEntity attacker, Vec3d pos, int reach, int maxSegments, Vec3d lookVector, DamageSource damageSource, List<MagnitudeEffectType> magnitudeEffectTypes) {
        Vec3d attackerPos = attacker.getEyePos();
        BlockPos blockPos = new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
        List<Box> boxList = new ArrayList<>();
        
        world.getBlockState(blockPos).getCollisionShape(world, blockPos).forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // inBox = minX + blockPos.getX() < pos.x && maxX + blockPos.getX() > pos.x && minY + blockPos.getY() < pos.y && maxY + blockPos.getY() > pos.y && minZ + blockPos.getZ() < pos.z && maxZ + blockPos.getZ() > pos.z;
            boxList.add(new Box(minX, minY, minZ, maxX, maxY, maxZ));
        
        });

        for (Box box : boxList) {
            Box shiftedBox = box.offset(blockPos);
            
            if (shiftedBox.contains(pos)) {
                return true;
            }
        }

        List<LivingEntity> entities = world.getEntitiesByType(
            TypeFilter.instanceOf(LivingEntity.class), 
            Box.of(pos, 10, 10, 10), 
            e -> (!e.equals(attacker)) && e.getBoundingBox().contains(pos)
        );
        LivingEntity target;

        // EnchantedExpanded.LOGGER.info("magnitude found entites:" + entities);

        if (entities.size() == 0) {
            return false;

        } else if (entities.size() == 1) {
            target = entities.get(0);

        } else {
            double shortestDistance = Double.POSITIVE_INFINITY;
            LivingEntity nearestEntity = null;

            for (LivingEntity e : entities) {
                double entityDistance = e.getPos().distanceTo(attackerPos);

                if (entityDistance < shortestDistance) {
                    shortestDistance = entityDistance;
                    nearestEntity = e;
                }
            }
            
            target = nearestEntity;
        }

        float cooldownProgress = attacker instanceof PlayerEntity ? ((PlayerEntity) attacker).getAttackCooldownProgress(0.5f) : 1;
        float damageModifier = cooldownProgress * (0.5f + (1 - ((float) reach / (float) maxSegments)) / 2f);
    
        attacker.getMainHandStack().damage(1, attacker, player -> player.sendToolBreakStatus(player.getActiveHand()));

        if (!(target.hurtTime < 10 && target.isAttackable() && !(target.isInvulnerableTo(damageSource)))) return false;

        if (magnitudeEffectTypes.size() == 0) {
            target.damage(damageSource, damageModifier * ((float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) + EnchantmentHelper.getAttackDamage(attacker.getMainHandStack(), target.getGroup())));
            target.takeKnockback(0.4f, MathHelper.sin(attacker.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(attacker.getYaw() * ((float)Math.PI / 180)));
        } else {
            target.damage(damageSource, 0.5f);

            for (MagnitudeEffectType met : magnitudeEffectTypes) {
                met.onAttack(attacker, target);
            }
        }

        return true;
    }

    private static enum MagnitudeEffectType {
        FIRE_ASPECT(Enchantments.FIRE_ASPECT, ParticleTypes.FLAME, (attacker, target) -> {
            target.setOnFireFor(EnchantmentHelper.getFireAspect(attacker) * 4);
        }),
        FROST_ASPECT(EEEnchantments.FROST_ASPECT, ParticleTypes.SNOWFLAKE, (attacker, target) -> {
            OnAttack.frostAspect(attacker, target);
        }),
        WITHERING(EEEnchantments.WITHERING, ParticleTypes.SMOKE, (attacker, target) -> {
            OnAttack.withering(attacker, target);
        });

        final Enchantment enchantment;
        final ParticleEffect particle;
        final BiConsumer<LivingEntity, LivingEntity> func;

        public ParticleEffect getParticle() {
            if (this == MagnitudeEffectType.FIRE_ASPECT) {
                return ParticleTypes.SMALL_FLAME;
            }

            return particle;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public void onAttack(LivingEntity attacker, LivingEntity target) {
            func.accept(attacker, target);
        }

        private <T extends ParticleEffect> MagnitudeEffectType(Enchantment enchantment, T particle, BiConsumer<LivingEntity, LivingEntity> func) {
            this.enchantment = enchantment;
            this.particle = particle;
            this.func = func;
        }
    }

    public static void spawnMagnitudeParticles(Vec3d pos, World world, List<MagnitudeEffectType> magnitudeEffectTypes) {
        if (!(world instanceof ServerWorld)) return;
        
        ServerWorld serverWorld = (ServerWorld) world;

        if (magnitudeEffectTypes.size() == 0) {
            spawnSingleParticle(serverWorld, pos, ParticleTypes.ENCHANTED_HIT);            
        }

        else {
            for (MagnitudeEffectType met : magnitudeEffectTypes) {
                spawnSingleParticle(serverWorld, pos, met.getParticle());
            }
        }
    }

    private static <T extends ParticleEffect> void spawnSingleParticle(ServerWorld world, Vec3d pos, T particle) {
        world.spawnParticles(
            particle, 
            pos.getX(), 
            pos.getY(), 
            pos.getZ(), 
            1, 
            0, 
            0, 
            0, 
            0
        );
    }

    // private static class BlockShapeTracker {
    //     BlockPos blockPos;
    //     List<Box> boxes;
    //     boolean hasCollided = false;

    //     public BlockShapeTracker(BlockPos blockPos, List<Box> boxes) {
    //         this.blockPos = blockPos;
    //         this.boxes = boxes;
    //     }

    //     public BlockShapeTracker(BlockPos blockPos, VoxelShape voxelShape) {
    //         List<Box> boxes = new ArrayList<>();
            
    //         voxelShape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
    //             boxes.add(new Box(minX, minY, minZ, maxX, maxY, maxZ));
    //         });

    //         this.blockPos = blockPos;
    //         this.boxes = boxes;
    //     }

    //     public BlockShapeTracker(World world, BlockPos blockPos) {
    //         this(blockPos, world.getBlockState(blockPos).getCollisionShape(world, blockPos));
    //     }

    //     private boolean collidesWith(World world, Vec3d pos) {
    //         if (!(new BlockPos(pos).equals(blockPos))) {
                
    //         }

    //         for (Box box : boxes) {
    //         Box shiftedBox = box.offset(blockPos);
            
    //         if (shiftedBox.contains(pos)) {
    //             return true;
    //         }
    //     }
    // }
}
