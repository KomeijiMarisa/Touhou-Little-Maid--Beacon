package com.mastermarisa.maidbeacon.client.event;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.client.render.MaidExtraRenderer;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MaidBeacon.MOD_ID, value = Dist.CLIENT)
public class OnClientSetup {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ExtraRenderingEntity.TYPE, MaidExtraRenderer::new);
    }
}
