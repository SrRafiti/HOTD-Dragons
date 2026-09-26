package com.rafiti.hotddragons.client.renderer;

import com.rafiti.hotddragons.client.model.CaraxesModel;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class CaraxesRenderer extends GeoEntityRenderer<CaraxesEntity> {
    public CaraxesRenderer(EntityRendererProvider.Context context) {
        super(context, new CaraxesModel());
        this.shadowRadius = 2.5F;
    }
}
