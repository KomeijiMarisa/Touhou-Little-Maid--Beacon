package com.mastermarisa.maidbeacon.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.entity.ExtraRenderingEntity;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

@EventBusSubscriber(modid = MaidBeacon.MOD_ID)
public class MaidTracker {
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            ExtraRenderingEntity extraRendering = new ExtraRenderingEntity(ExtraRenderingEntity.TYPE, event.getLevel());
            extraRendering.setOwnerID(maid.getId());
            extraRendering.setPos(maid.position());
            event.getLevel().addFreshEntity(extraRendering);
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            ServerLevel level = (ServerLevel) event.getLevel();
            level.getEntities().getAll().forEach(e -> {
                if (e instanceof ExtraRenderingEntity extraRendering && extraRendering.getOwnerID() == maid.getId()) {
                    extraRendering.discard();
                }
            });
        }
    }
}
