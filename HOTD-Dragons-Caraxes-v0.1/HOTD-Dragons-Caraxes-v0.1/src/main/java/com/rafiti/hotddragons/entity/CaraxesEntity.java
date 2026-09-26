package com.rafiti.hotddragons.entity;

import com.leon.saintsdragons.server.entity.dragons.ignivorus.Ignivorus;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * Caraxes v0.1 prototype.
 *
 * The first playable milestone intentionally extends Ignivorus so the addon
 * can reuse Saint's Dragons' mature riding, flight and combat machinery while
 * keeping all visual assets in this addon original.
 *
 * Future milestones will replace inherited behavior with Caraxes-specific
 * attributes, attacks, bond progression, rider seat geometry and AI.
 */
public final class CaraxesEntity extends Ignivorus {
    public CaraxesEntity(EntityType<? extends CaraxesEntity> type, Level level) {
        super(type, level);
    }
}
