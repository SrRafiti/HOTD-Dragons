package com.rafiti.hotddragons;

import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import com.rafiti.hotddragons.registry.ModEntities;
import com.rafiti.hotddragons.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HOTDDragons.MOD_ID)
public final class HOTDDragons {
    public static final String MOD_ID = "hotddragons";

    public HOTDDragons() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITY_TYPES.register(modBus);
        ModItems.ITEMS.register(modBus);

        modBus.addListener(this::registerAttributes);
        modBus.addListener(this::addCreativeTabEntries);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        // Prototype: Caraxes temporarily inherits Ignivorus' combat and flight attributes.
        event.put(ModEntities.CARAXES.get(), Ignivorus.createAttributes().build());
    }

    private void addCreativeTabEntries(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(ModItems.CARAXES_SPAWN_EGG.get());
        }
    }
}
