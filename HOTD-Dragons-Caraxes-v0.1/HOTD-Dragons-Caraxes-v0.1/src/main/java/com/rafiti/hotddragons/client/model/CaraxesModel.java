package com.rafiti.hotddragons.client.model;

import com.rafiti.hotddragons.HOTDDragons;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public final class CaraxesModel extends GeoModel<CaraxesEntity> {
    private static final ResourceLocation MODEL =
            new ResourceLocation(HOTDDragons.MOD_ID, "geo/caraxes.geo.json");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HOTDDragons.MOD_ID, "textures/entity/caraxes.png");
    private static final ResourceLocation ANIMATION =
            new ResourceLocation(HOTDDragons.MOD_ID, "animations/caraxes.animation.json");

    @Override
    public ResourceLocation getModelResource(CaraxesEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(CaraxesEntity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(CaraxesEntity animatable) {
        return ANIMATION;
    }
}
