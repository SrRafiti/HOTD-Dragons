package com.rafiti.hotddragons.registry;

import com.rafiti.hotddragons.HOTDDragons;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HOTDDragons.MOD_ID);

    public static final RegistryObject<EntityType<CaraxesEntity>> CARAXES =
            ENTITY_TYPES.register("caraxes",
                    () -> EntityType.Builder
                            .of(CaraxesEntity::new, MobCategory.CREATURE)
                            .sized(3.0F, 3.0F)
                            .clientTrackingRange(16)
                            .updateInterval(1)
                            .build(HOTDDragons.MOD_ID + ":caraxes"));

    private ModEntities() {}
}
