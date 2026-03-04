package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModEntities {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MaidBeacon.MOD_ID);

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.
            create(BuiltInRegistries.ENTITY_TYPE, MaidBeacon.MOD_ID);

    public static final Supplier<AttachmentType<ExtraRenderSettings>> EXTRA_RENDER_SETTINGS = ATTACHMENT_TYPES.register("extra_render_settings", () -> ExtraRenderSettings.TYPE);

    public static final DeferredHolder<EntityType<?>, EntityType<ExtraRenderingEntity>> EXTRA_RENDERING_ENTITY = ENTITY_TYPES.
            register("extra_rendering_entity", () -> ExtraRenderingEntity.TYPE);

    public static void register(IEventBus mod) {
        ATTACHMENT_TYPES.register(mod);
        ENTITY_TYPES.register(mod);
    }
}
