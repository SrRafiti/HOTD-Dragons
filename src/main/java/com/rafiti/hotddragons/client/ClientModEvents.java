package com.rafiti.hotddragons.client;

import com.leon.saintsdragons.client.camera.DragonRideCameraTuning;
import com.leon.saintsdragons.client.renderer.RiderConfig;
import com.rafiti.hotddragons.HOTDDragons;
import com.rafiti.hotddragons.client.renderer.CaraxesRenderer;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import com.rafiti.hotddragons.registry.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@Mod.EventBusSubscriber(
        modid = HOTDDragons.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientModEvents {
    private ClientModEvents() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CARAXES.get(), CaraxesRenderer::new);

        // IMPORTANT: Caraxes inherits from Ignivorus, so without an exact
        // registration Saint's Dragons walks up the class tree and gives him
        // Ignivorus' giant rider/camera offsets. Registering the exact class
        // stops that inheritance leak.
        RiderConfig.RiderSpec riderSpec = new RiderConfig.RiderSpec(
                "chest",
                new Vector3f(0.0F, 0.15F, 0.05F),
                new Vector3f(0.0F, 5.0F, -2.0F),
                250L,
                64.0D,
                -180.0F
        )
                .withLocator(0, "caraxesRider", new Vector3f(0.0F, 3.0F, 0.0F))
                .withCamera(true, false, 0.20D);

        RiderConfig.register(CaraxesEntity.class, riderSpec);

        // Much closer than Ignivorus' 25/30 block camera. This is tuned for
        // the current Caraxes blockout and can be widened later as the adult
        // model becomes physically larger.
        DragonRideCameraTuning.CameraProfile caraxesCamera =
                new DragonRideCameraTuning.CameraProfile(
                        5.0F,   // grounded distance
                        7.5F,   // air distance
                        2.0F,   // bank shift max
                        0.08F,  // zoom smoothing
                        0.15D,  // lateral shift smoothing
                        0.12D,  // vertical shift smoothing
                        0.35D,  // grounded vertical shift
                        1.20D,  // air vertical shift
                        2.0F,   // grounded pitch offset
                        6.0F,   // air pitch offset
                        0.15F   // pitch smoothing
                );

        DragonRideCameraTuning.register(
                new ResourceLocation(HOTDDragons.MOD_ID, "caraxes"),
                CaraxesEntity.class,
                caraxesCamera
        );
    }
}
