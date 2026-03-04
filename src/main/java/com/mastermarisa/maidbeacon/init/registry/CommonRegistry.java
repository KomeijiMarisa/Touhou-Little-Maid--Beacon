package com.mastermarisa.maidbeacon.init.registry;

import com.github.tartaricacid.touhoulittlemaid.init.InitCreativeTabs;
import com.mastermarisa.maidbeacon.init.ModItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber
public final class CommonRegistry {
    @SubscribeEvent
    public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == InitCreativeTabs.MAIN_TAB.getKey()){
            event.accept(ModItems.MOBILE_BEACON.get());
        }
    }
}
