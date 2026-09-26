package com.rafiti.hotddragons.client.renderer;

import com.leon.saintsdragons.client.renderer.DragonGeoEntityRenderer;
import com.rafiti.hotddragons.client.model.CaraxesModel;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public final class CaraxesRenderer extends DragonGeoEntityRenderer<CaraxesEntity> {
    public CaraxesRenderer(EntityRendererProvider.Context context) {
        super(context, new CaraxesModel());
    }

    @Override
    protected float getRenderScale(CaraxesEntity entity) {
        // Using Saint's Dragons' renderer base is important here: it means the
        // rider attachment/camera system sees the same scale as the model.
        return entity.getCaraxesScale();
    }

    @Override
    protected float getBabyShadowRadius(CaraxesEntity entity) {
        return 2.3F * entity.getCaraxesScale();
    }

    @Override
    protected float getAdultShadowRadius(CaraxesEntity entity) {
        return 2.3F * entity.getCaraxesScale();
    }

    @Override
    protected String[] trackedBoneNames() {
        // The chest is our rider anchor for v0.2.1.
        return new String[]{"chest"};
    }
}
