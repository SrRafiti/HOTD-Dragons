package com.rafiti.hotddragons;

import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Temporary compatibility shim for the prototype.
 *
 * Caraxes currently subclasses Ignivorus to reuse its flight engine. Saint's
 * Dragons therefore runs IgnivorusRiderController, whose hard-coded seat is
 * roughly Y=5.2 / Z=12.5 blocks from the entity. v0.2.2 corrects the rider at
 * the end of each player tick, after the inherited controller has positioned
 * the passenger. Once Caraxes moves to its own rider controller this shim can
 * be removed.
 */
@Mod.EventBusSubscriber(modid = HOTDDragons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CaraxesRiderEvents {
    private CaraxesRiderEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;
        if (!(player.getVehicle() instanceof CaraxesEntity caraxes)) {
            return;
        }

        // Keep the physical player on Caraxes' shoulders/base of neck. Doing
        // this on both logical sides prevents the client from visually drifting
        // toward Ignivorus' inherited 12.5-block-forward seat.
        double yaw = Math.toRadians(caraxes.getYRot());
        double localForward = -0.10D * Math.max(0.55D, caraxes.getCaraxesScale());
        double dx = -Math.sin(yaw) * localForward;
        double dz =  Math.cos(yaw) * localForward;
        double seatY = caraxes.getY() + Math.max(0.62D, caraxes.getBbHeight() * 0.62D);

        player.setPos(caraxes.getX() + dx, seatY, caraxes.getZ() + dz);
        player.fallDistance = 0.0F;
    }

    @SubscribeEvent
    public static void onMount(EntityMountEvent event) {
        if (!event.isMounting()) {
            return;
        }
        if (!(event.getEntityMounting() instanceof Player player)) {
            return;
        }
        if (!(event.getEntityBeingMounted() instanceof CaraxesEntity)) {
            return;
        }
        if (!player.level().isClientSide) {
            player.displayClientMessage(
                    Component.literal("[HOTD Dragons v0.2.2] Asiento de Caraxes activo"),
                    true
            );
        }
    }
}
