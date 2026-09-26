package com.rafiti.hotddragons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rafiti.hotddragons.client.model.CaraxesModel;
import com.rafiti.hotddragons.entity.CaraxesEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public final class CaraxesRenderer extends GeoEntityRenderer<CaraxesEntity> {
    public CaraxesRenderer(EntityRendererProvider.Context context) {
        super(context, new CaraxesModel());
        this.shadowRadius = 2.5F;
    }

    @Override
    public void render(
            CaraxesEntity entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight
    ) {
        float scale = entity.getCaraxesScale();

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
