package com.mastermarisa.maidbeacon.network;

import com.mastermarisa.maidbeacon.MaidBeacon;
import com.mastermarisa.maidbeacon.config.Config;
import com.mastermarisa.maidbeacon.data.MobileBeaconData;
import com.mastermarisa.maidbeacon.init.ModDataComponents;
import com.mastermarisa.maidbeacon.init.ModItems;
import com.mastermarisa.maidbeacon.utils.MaidUtils;
import com.mastermarisa.maidbeacon.utils.StackPredicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record UpgradeBeaconPayload(int index, UUID uuid, int hand) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpgradeBeaconPayload> TYPE =
            new CustomPacketPayload.Type<>(MaidBeacon.resourceLocation("upgrade_beacon"));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, UpgradeBeaconPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    UpgradeBeaconPayload::index,
                    NetworkHandler.UUID_STREAM_CODEC,
                    UpgradeBeaconPayload::uuid,
                    ByteBufCodecs.INT,
                    UpgradeBeaconPayload::hand,
                    UpgradeBeaconPayload::new
            );

    public static void handle(UpgradeBeaconPayload payload, IPayloadContext context) {
        ServerLevel level = (ServerLevel) context.player().level();
        ServerPlayer player = (ServerPlayer) level.getPlayerByUUID(payload.uuid());
        if (player != null) {
            ItemStack itemInHand = player.getItemInHand(payload.hand == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            if (itemInHand.is(ModItems.MOBILE_BEACON.get())) {
                MobileBeaconData mobileBeaconData = itemInHand.getOrDefault(ModDataComponents.MOBILE_BEACON_DATA.get(), new MobileBeaconData());
                if (mobileBeaconData.getLevel() == payload.index) {
                    ItemStack required = Config.ITEMSTACK_TO_UPGRADE_BEACON_LEVEL().get(payload.index);
                    List<ItemStack> itemStackList = MaidUtils.tryExtract(player.getCapability(Capabilities.ItemHandler.ENTITY), required.getCount(), StackPredicate.of(required.getItem()), true);
                    if (!itemStackList.isEmpty()) {
                        mobileBeaconData.setLevel(mobileBeaconData.getLevel() + 1);
                    }
                }
                itemInHand.set(ModDataComponents.MOBILE_BEACON_DATA.get(), mobileBeaconData);
            }
        }
    }
}
