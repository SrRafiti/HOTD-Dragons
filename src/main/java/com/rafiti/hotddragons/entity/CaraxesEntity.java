package com.rafiti.hotddragons.entity;

import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * Caraxes v0.2.
 *
 * Saint's Dragons is still used as the temporary flight/combat engine, but
 * growth and rider placement are now owned by this addon instead of relying
 * on Ignivorus' model proportions.
 */
public final class CaraxesEntity extends Ignivorus {
    private static final EntityDataAccessor<Integer> GROWTH_TICKS =
            SynchedEntityData.defineId(CaraxesEntity.class, EntityDataSerializers.INT);

    /** Ten real minutes from hatchling to adult. */
    public static final int MAX_GROWTH_TICKS = 20 * 60 * 10;

    /** One blaze powder advances one minute, useful while testing v0.2. */
    private static final int TEST_GROWTH_BOOST = 20 * 60;

    private int lastGrowthStage = -1;

    public CaraxesEntity(EntityType<? extends CaraxesEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GROWTH_TICKS, 0);
    }

    @Override
    public void tick() {
        super.tick();

        // Sync growth once per second instead of every tick to keep network
        // traffic small while still looking smooth enough for this prototype.
        if (!this.level().isClientSide && this.tickCount % 20 == 0 && !isFullyGrown()) {
            setGrowthTicks(getGrowthTicks() + 20);
        }

        int stage = getGrowthStage();
        if (stage != lastGrowthStage) {
            lastGrowthStage = stage;
            this.refreshDimensions();
        }
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        if (!this.hasPassenger(passenger)) {
            return;
        }

        float scale = getCaraxesScale();

        // Seat is centered over the shoulders/base of the neck. The v0.1
        // inherited Ignivorus seat expected a much taller/different model,
        // which is why the player appeared many blocks above Caraxes.
        double yaw = Math.toRadians(this.getYRot());
        double rearward = 0.10D * scale;
        double seatX = this.getX() + Math.sin(yaw) * rearward;
        double seatZ = this.getZ() - Math.cos(yaw) * rearward;
        double seatY = this.getY() + (1.15D * scale) + passenger.getMyRidingOffset();

        moveFunction.accept(passenger, seatX, seatY, seatZ);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        // Stage-based collision avoids recalculating a different hitbox every
        // second while the visual model can still grow continuously.
        float hitboxScale = switch (getGrowthStage()) {
            case 0 -> 0.38F;
            case 1 -> 0.58F;
            case 2 -> 0.78F;
            default -> 1.0F;
        };
        return super.getDimensions(pose).scale(hitboxScale);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Temporary v0.2 testing shortcut: sneak + blaze powder accelerates
        // growth by one minute. It is intentionally gated behind sneaking so
        // it does not steal normal Saint's Dragons interactions/taming.
        if (player.isShiftKeyDown() && stack.is(Items.BLAZE_POWDER) && !isFullyGrown()) {
            if (!this.level().isClientSide) {
                setGrowthTicks(getGrowthTicks() + TEST_GROWTH_BOOST);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    public int getGrowthTicks() {
        return this.entityData.get(GROWTH_TICKS);
    }

    public void setGrowthTicks(int ticks) {
        this.entityData.set(GROWTH_TICKS, Math.max(0, Math.min(MAX_GROWTH_TICKS, ticks)));
    }

    public boolean isFullyGrown() {
        return getGrowthTicks() >= MAX_GROWTH_TICKS;
    }

    /**
     * Continuous render scale: 35% at hatching, 100% as an adult.
     */
    public float getCaraxesScale() {
        float progress = getGrowthTicks() / (float) MAX_GROWTH_TICKS;
        progress = Math.max(0.0F, Math.min(1.0F, progress));
        return 0.35F + (0.65F * progress);
    }

    /**
     * Four coarse stages used for hitboxes and later stat progression.
     */
    public int getGrowthStage() {
        float progress = getGrowthTicks() / (float) MAX_GROWTH_TICKS;
        if (progress < 0.25F) return 0; // hatchling
        if (progress < 0.55F) return 1; // juvenile
        if (progress < 0.85F) return 2; // subadult
        return 3;                       // adult
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CaraxesGrowth", getGrowthTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("CaraxesGrowth", Tag.TAG_INT)) {
            setGrowthTicks(tag.getInt("CaraxesGrowth"));
        } else {
            // Compatibility with Caraxes entities saved by v0.1: keep those
            // as adults rather than suddenly shrinking them into hatchlings.
            setGrowthTicks(MAX_GROWTH_TICKS);
        }

        this.refreshDimensions();
    }
}
