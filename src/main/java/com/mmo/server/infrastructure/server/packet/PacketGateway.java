package com.mmo.server.infrastructure.server.packet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.mmo.server.core.packet.NetworkPacket;
import com.mmo.server.core.packet.Packet;

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

    public <T extends NetworkPacket> PacketConverter<T> getConverter(UUID alias)
            throws PacketConverterCastException, PacketConverterNotBindedException {

        return Optional.ofNullable(converters.get(alias))
                .map(converter -> this.<T>cast(converter))
                .orElseThrow(
                        () -> new PacketConverterNotBindedException("PacketReaderConverter not binded for alias %s",
                                alias));
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

    public <T extends NetworkPacket> T read(String alias, UUID source, byte[] bytes) {
        return read(getAliasAsUUID(alias), source, bytes);
    }

    public <T extends NetworkPacket> T read(UUID alias, UUID source, byte[] bytes) {
        PacketConverter<T> converter = this.<T>getConverter(alias);

        return converter.read(source, bytes);
    }

    public <T extends NetworkPacket> byte[] write(T packet) {
        PacketConverter<T> converter = this.<T>getConverter(getAliasAsUUID(packet.getAlias()));

        return converter.write(packet);
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
