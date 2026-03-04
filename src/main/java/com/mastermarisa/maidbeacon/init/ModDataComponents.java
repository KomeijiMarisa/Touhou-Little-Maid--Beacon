package com.mastermarisa.maidbeacon.init;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS;

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MobileBeaconData>> MOBILE_BEACON_DATA;

    public static void register(IEventBus mod) {
        DATA_COMPONENTS.register(mod);
    }

    static {
        DATA_COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MaidBeacon.MOD_ID);
        MOBILE_BEACON_DATA = DATA_COMPONENTS.register("mobile_beacon_data", () -> DataComponentType.<MobileBeaconData>builder().persistent(MobileBeaconData.CODEC).networkSynchronized(MobileBeaconData.STREAM_CODEC).build());
    }
}
