package com.rafiti.hotddragons.entity;

import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
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
 * Caraxes v0.2.1.
 *
 * Saint's Dragons still supplies the temporary flight/combat engine. This
 * class owns Caraxes' visual growth, collision scaling and physical rider
 * position. Client rider rendering/camera are registered separately so they
 * no longer inherit Ignivorus' huge offsets.
 */
public final class CaraxesEntity extends Ignivorus {
    private static final EntityDataAccessor<Integer> GROWTH_TICKS =
            SynchedEntityData.defineId(CaraxesEntity.class, EntityDataSerializers.INT);

    /** Ten real minutes from hatchling to adult for prototype testing. */
    public static final int MAX_GROWTH_TICKS = 20 * 60 * 10;

    /** Four blaze powders take a fresh Caraxes from hatchling to adult. */
    private static final int TEST_GROWTH_BOOST = MAX_GROWTH_TICKS / 4;

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

        // Normal prototype growth: +1 second every real second.
        if (!this.level().isClientSide && this.tickCount % 20 == 0 && !isFullyGrown()) {
            setGrowthTicks(getGrowthTicks() + 20);
        }

        // Both sides refresh hitbox when crossing one of the four stages.
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
        double yaw = Math.toRadians(this.getYRot());

        // Physical player location on the server. The renderer has a matching
        // chest-bone attachment so the visible rider and camera stay together.
        double rearward = 0.20D * scale;
        double seatX = this.getX() + Math.sin(yaw) * rearward;
        double seatZ = this.getZ() - Math.cos(yaw) * rearward;
        double seatY = this.getY() + (0.95D * scale) + passenger.getMyRidingOffset();

        moveFunction.accept(passenger, seatX, seatY, seatZ);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        float hitboxScale = switch (getGrowthStage()) {
            case 0 -> 0.35F;
            case 1 -> 0.55F;
            case 2 -> 0.78F;
            default -> 1.0F;
        };
        return super.getDimensions(pose).scale(hitboxScale);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // TEST CONTROL: sneak + blaze powder adds exactly 25% growth.
        if (player.isShiftKeyDown() && stack.is(Items.BLAZE_POWDER)) {
            if (!this.level().isClientSide) {
                if (!isFullyGrown()) {
                    setGrowthTicks(getGrowthTicks() + TEST_GROWTH_BOOST);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                showGrowthDebug(player);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // TEST CONTROL: sneak + charcoal resets any Caraxes, including old
        // v0.1/v0.2 adults, so growth can be checked without spawning another.
        if (player.isShiftKeyDown() && stack.is(Items.CHARCOAL)) {
            if (!this.level().isClientSide) {
                setGrowthTicks(0);
                showGrowthDebug(player);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    private void showGrowthDebug(Player player) {
        int percent = Math.round(getGrowthProgress() * 100.0F);
        player.displayClientMessage(
                Component.literal("Caraxes: " + percent + "% - " + getGrowthStageName()),
                true
        );
    }

    public int getGrowthTicks() {
        return this.entityData.get(GROWTH_TICKS);
    }

    public void setGrowthTicks(int ticks) {
        this.entityData.set(GROWTH_TICKS, Math.max(0, Math.min(MAX_GROWTH_TICKS, ticks)));
    }

    public float getGrowthProgress() {
        return Math.max(0.0F, Math.min(1.0F, getGrowthTicks() / (float) MAX_GROWTH_TICKS));
    }

    public boolean isFullyGrown() {
        return getGrowthTicks() >= MAX_GROWTH_TICKS;
    }

    /** Continuous render scale: 30% at hatching, 100% as an adult. */
    public float getCaraxesScale() {
        return 0.30F + (0.70F * getGrowthProgress());
    }

    public int getGrowthStage() {
        float progress = getGrowthProgress();
        if (progress < 0.25F) return 0;
        if (progress < 0.55F) return 1;
        if (progress < 0.85F) return 2;
        return 3;
    }

    public String getGrowthStageName() {
        return switch (getGrowthStage()) {
            case 0 -> "cria";
            case 1 -> "juvenil";
            case 2 -> "subadulto";
            default -> "adulto";
        };
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
            // Old Caraxes stay adult unless the tester explicitly resets them
            // with sneak + charcoal.
            setGrowthTicks(MAX_GROWTH_TICKS);
        }

        this.refreshDimensions();
    }
}
