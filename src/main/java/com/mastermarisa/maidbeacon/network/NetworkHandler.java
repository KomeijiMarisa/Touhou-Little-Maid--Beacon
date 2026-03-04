package com.mastermarisa.maidbeacon.network;

import com.mastermarisa.maidbeacon.MaidBeacon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Objects;
import java.util.UUID;

@EventBusSubscriber(modid = MaidBeacon.MOD_ID)
public class NetworkHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToServer(
                OperateEffectPayload.TYPE,
                OperateEffectPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> OperateEffectPayload.handle(payload, context));
                }
        );

        registrar.playToServer(
                UpgradeBeaconPayload.TYPE,
                UpgradeBeaconPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> UpgradeBeaconPayload.handle(payload, context));
                }
        );

        registrar.playToServer(
                SyncRenderSettingsPayload.TYPE,
                SyncRenderSettingsPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> SyncRenderSettingsPayload.handle(payload, context));
                }
        );
    }

    public static final StreamCodec<FriendlyByteBuf, UUID> UUID_STREAM_CODEC = StreamCodec.of(
            (buf, uuid) -> buf.writeUUID(uuid),
            buf -> buf.readUUID()
    );

    public static final StreamCodec<FriendlyByteBuf, CompoundTag> COMPOUND_TAG_STREAM_CODEC = StreamCodec.of(
            (buf, tag) -> buf.writeNbt(tag),
            buf -> Objects.requireNonNull(buf.readNbt())
    );
}
