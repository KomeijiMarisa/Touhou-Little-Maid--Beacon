package com.mastermarisa.maidbeacon.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.client.gui.screen.rendering.RenderSettingsScreen;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = MaidBeacon.MOD_ID)
public class OnInteractMaid {
    @SubscribeEvent
    public static void onPlayerInteractMaidEvent(PlayerInteractEvent.EntityInteract event){
        if (event.getTarget() instanceof EntityMaid maid) {
            ItemStack itemStack = event.getItemStack();
            if (itemStack.is(ModItems.MOBILE_BEACON.get())) {
                if (event.getLevel().isClientSide())
                    RenderSettingsScreen.open(maid);
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
