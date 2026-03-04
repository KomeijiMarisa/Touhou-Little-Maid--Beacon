package com.mastermarisa.maidbeacon.network;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.data.ExtraRenderSettings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record SyncRenderSettingsPayload(UUID uuid, CompoundTag compoundTag) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncRenderSettingsPayload> TYPE =
            new CustomPacketPayload.Type<>(MaidBeacon.resourceLocation("sync_render_settings"));

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, SyncRenderSettingsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    NetworkHandler.UUID_STREAM_CODEC,
                    SyncRenderSettingsPayload::uuid,
                    NetworkHandler.COMPOUND_TAG_STREAM_CODEC,
                    SyncRenderSettingsPayload::compoundTag,
                    SyncRenderSettingsPayload::new
            );

    public static void handle(SyncRenderSettingsPayload payload, IPayloadContext context) {
        ServerLevel level = (ServerLevel) context.player().level();
        if (level.getEntity(payload.uuid()) instanceof EntityMaid maid) {
            ExtraRenderSettings settings = new ExtraRenderSettings();
            settings.deserializeNBT(level.registryAccess(), payload.compoundTag());
            maid.setData(ExtraRenderSettings.TYPE, settings);
        }
    }
}
