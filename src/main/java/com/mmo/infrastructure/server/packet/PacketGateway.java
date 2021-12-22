package com.mmo.infrastructure.server.packet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.mmo.core.packet.NetworkPacket;
import com.mmo.core.packet.Packet;

public class PacketGateway {

    private static PacketGateway instance;
    private final Map<UUID, PacketConverter<? extends NetworkPacket>> converters = new LinkedHashMap<>();

    public static PacketGateway getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PacketGateway();
        }

        return instance;
    }

    private PacketGateway() {

    }

    public <T extends NetworkPacket> PacketConverter<T> getBuilder(UUID alias)
            throws PacketConverterCastException, PacketConverterNotBindedException {

        return Optional.ofNullable(converters.get(alias))
                .map(converter -> this.<T>cast(converter))
                .orElseThrow(
                        () -> new PacketConverterNotBindedException("PacketBuilder not binded for alias %s", alias));
    }

    public <T extends NetworkPacket> PacketGateway bind(Packet packet, PacketConverter<T> converter) {
        return bind(packet.getAliasAsUUID(), converter);
    }

    public <T extends NetworkPacket> PacketGateway bind(String alias, PacketConverter<T> converter) {
        return bind(getAliasAsUUID(alias), converter);
    }

    public <T extends NetworkPacket> PacketGateway bind(UUID alias, PacketConverter<T> converter) {
        converters.put(alias, converter);
        return this;
    }

    public <T extends NetworkPacket> T in(String alias, UUID source, byte[] bytes) {
        return in(getAliasAsUUID(alias), source, bytes);
    }

    public <T extends NetworkPacket> T in(UUID alias, UUID source, byte[] bytes) {
        PacketConverter<T> converter = this.<T>getBuilder(alias);

        return converter.fromBytes(source, bytes);
    }

    public <T extends NetworkPacket> byte[] out(T packet) {
        PacketConverter<T> converter = this.<T>getBuilder(getAliasAsUUID(packet.getAlias()));

        return converter.toBytes(packet);
    }

    @SuppressWarnings("unchecked")
    private <T extends NetworkPacket> PacketConverter<T> cast(PacketConverter<?> converter) {
        try {
            return (PacketConverter<T>) converter;
        } catch (Exception exception) {
            throw new PacketConverterCastException(exception, "Failed to cast converter");
        }
    }

    private UUID getAliasAsUUID(String alias) {
        return UUID.nameUUIDFromBytes(alias.getBytes());
    }
}
