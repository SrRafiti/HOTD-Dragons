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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Register after the mod classes are ready, but before gameplay. This
        // exact-class registration takes priority over the inherited Ignivorus
        // entries when Saint's Dragons resolves rider/camera configuration.
        event.enqueueWork(() -> {
            RiderConfig.RiderSpec riderSpec = new RiderConfig.RiderSpec(
                    "chest",
                    new Vector3f(0.0F, 0.10F, 0.0F),
                    new Vector3f(0.0F, 2.2F, -0.8F),
                    250L,
                    48.0D,
                    -180.0F
            )
                    .withLocator(0, "caraxesRider", new Vector3f(0.0F, 1.5F, 0.0F))
                    .withCamera(true, false, 0.10D);

            RiderConfig.register(CaraxesEntity.class, riderSpec);

            DragonRideCameraTuning.CameraProfile caraxesCamera =
                    new DragonRideCameraTuning.CameraProfile(
                            4.0F,   // grounded distance
                            6.0F,   // air distance
                            1.5F,   // bank shift max
                            0.08F,  // zoom smoothing
                            0.15D,  // lateral shift smoothing
                            0.12D,  // vertical shift smoothing
                            0.25D,  // grounded vertical shift
                            0.70D,  // air vertical shift
                            1.0F,   // grounded pitch offset
                            4.0F,   // air pitch offset
                            0.15F   // pitch smoothing
                    );

            DragonRideCameraTuning.register(
                    new ResourceLocation(HOTDDragons.MOD_ID, "caraxes_v022"),
                    CaraxesEntity.class,
                    caraxesCamera
            );
        });
    }
}
