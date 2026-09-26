package com.rafiti.hotddragons.registry;

import com.rafiti.hotddragons.HOTDDragons;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HOTDDragons.MOD_ID);

    public static final RegistryObject<Item> CARAXES_SPAWN_EGG =
            ITEMS.register("caraxes_spawn_egg",
                    () -> new ForgeSpawnEggItem(
                            ModEntities.CARAXES,
                            0x4A0B0B,
                            0xD79A39,
                            new Item.Properties()
                    ));

    private ModItems() {}
}
