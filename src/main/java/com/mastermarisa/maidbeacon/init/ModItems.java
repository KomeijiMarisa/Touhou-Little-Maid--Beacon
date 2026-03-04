package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.item.MobileBeaconItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS;

    public static final DeferredItem<Item> MOBILE_BEACON;

    public static void register(IEventBus mod){
        ITEMS.register(mod);
    }

    static {
        ITEMS = DeferredRegister.createItems(MaidBeacon.MOD_ID);
        MOBILE_BEACON = ITEMS.registerItem("mobile_beacon", (properties) -> new MobileBeaconItem());
    }
}
