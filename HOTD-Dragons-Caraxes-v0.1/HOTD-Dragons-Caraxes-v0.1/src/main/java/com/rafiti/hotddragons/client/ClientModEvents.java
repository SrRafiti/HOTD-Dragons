package com.rafiti.hotddragons.client;

import com.rafiti.hotddragons.HOTDDragons;
import com.rafiti.hotddragons.client.renderer.CaraxesRenderer;
import com.rafiti.hotddragons.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
}
