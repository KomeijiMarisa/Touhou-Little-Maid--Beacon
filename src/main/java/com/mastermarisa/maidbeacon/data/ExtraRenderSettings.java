package com.mastermarisa.maidbeacon.data;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class ExtraRenderSettings implements INBTSerializable<CompoundTag> {
    public boolean enableRendering = true;
    public boolean enableBeaconBeam = true;
    public boolean enableBeaconModel = true;
    public boolean enableHeadHalo = false;
    public boolean enableSkyHalo = true;
    public byte headHaloStyle = 0;
    public byte skyHaloStyle = 0;

    @Override
    public CompoundTag serializeNBT(@Nullable HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("enable_rendering", enableRendering);
        tag.putBoolean("enable_beacon_beam", enableBeaconBeam);
        tag.putBoolean("enable_beacon_model", enableBeaconModel);
        tag.putBoolean("enable_head_halo", enableHeadHalo);
        tag.putBoolean("enable_sky_halo", enableSkyHalo);
        tag.putByte("head_halo_style", headHaloStyle);
        tag.putByte("sky_halo_style", skyHaloStyle);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("enable_rendering")) {
            enableRendering = tag.getBoolean("enable_rendering");
            enableBeaconBeam = tag.getBoolean("enable_beacon_beam");
            enableBeaconModel = tag.getBoolean("enable_beacon_model");
            enableHeadHalo = tag.getBoolean("enable_head_halo");
            enableSkyHalo = tag.getBoolean("enable_sky_halo");
            headHaloStyle = tag.getByte("head_halo_style");
            skyHaloStyle = tag.getByte("sky_halo_style");
        }
    }

    public List<Pair<String, String>> getDisplayPairs() {
        return List.of(
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_rendering").getString(), String.valueOf(enableRendering)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_beacon_beam").getString(), String.valueOf(enableBeaconBeam)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_beacon_model").getString(), String.valueOf(enableBeaconModel)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_head_halo").getString(), String.valueOf(enableHeadHalo)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.enable_sky_halo").getString(), String.valueOf(enableSkyHalo)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.head_halo_style").getString(), String.valueOf(headHaloStyle)),
                Pair.of(Component.translatable("gui.maidbeacon.render_setting.sky_halo_style").getString(), String.valueOf(skyHaloStyle))
        );
    }

    public static class Syncer implements AttachmentSyncHandler<ExtraRenderSettings> {
        @Override
        public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, ExtraRenderSettings extraRenderSettings, boolean b) {
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableRendering);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableBeaconBeam);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableBeaconModel);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableHeadHalo);
            registryFriendlyByteBuf.writeBoolean(extraRenderSettings.enableSkyHalo);
            registryFriendlyByteBuf.writeByte(extraRenderSettings.headHaloStyle);
            registryFriendlyByteBuf.writeByte(extraRenderSettings.skyHaloStyle);
        }

        @Override
        public @Nullable ExtraRenderSettings read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable ExtraRenderSettings extraRenderSettings) {
            if (extraRenderSettings == null) extraRenderSettings = new ExtraRenderSettings();
            extraRenderSettings.enableRendering = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableBeaconBeam = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableBeaconModel = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableHeadHalo = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.enableSkyHalo = registryFriendlyByteBuf.readBoolean();
            extraRenderSettings.headHaloStyle = registryFriendlyByteBuf.readByte();
            extraRenderSettings.skyHaloStyle = registryFriendlyByteBuf.readByte();
            return extraRenderSettings;
        }
    }

    public static final AttachmentType<ExtraRenderSettings> TYPE = AttachmentType
            .serializable(ExtraRenderSettings::new).sync(new Syncer()).build();
}
