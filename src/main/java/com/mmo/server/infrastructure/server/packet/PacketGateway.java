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
    private final Map<UUID, PacketReaderConverter<? extends NetworkPacket>> readerConverters = new LinkedHashMap<>();
    private final Map<UUID, PacketWriterConverter<? extends NetworkPacket>> writerConverters = new LinkedHashMap<>();

    public static PacketGateway getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PacketGateway();
        }

        return instance;
    }

    private PacketGateway() {

    }

    public <T extends NetworkPacket> PacketReaderConverter<T> getReader(UUID alias)
            throws PacketConverterCastException, PacketConverterNotBindedException {

        return Optional.ofNullable(readerConverters.get(alias))
                .map(converter -> this.<T>cast(converter))
                .orElseThrow(
                        () -> new PacketConverterNotBindedException("PacketReaderConverter not binded for alias %s",
                                alias));
    }

    public <T extends NetworkPacket> PacketWriterConverter<T> getWriter(UUID alias)
            throws PacketConverterCastException, PacketConverterNotBindedException {

        return Optional.ofNullable(writerConverters.get(alias))
                .map(converter -> this.<T>cast(converter))
                .orElseThrow(
                        () -> new PacketConverterNotBindedException("PacketWriterConverter not binded for alias %s",
                                alias));
    }

    public <T extends NetworkPacket> PacketGateway bindReader(Packet packet, PacketReaderConverter<T> converter) {
        return bindReader(packet.getAliasAsUUID(), converter);
    }

    public <T extends NetworkPacket> PacketGateway bindReader(String alias, PacketReaderConverter<T> converter) {
        return bindReader(getAliasAsUUID(alias), converter);
    }

    public <T extends NetworkPacket> PacketGateway bindReader(UUID alias, PacketReaderConverter<T> converter) {
        readerConverters.put(alias, converter);
        return this;
    }

    public <T extends NetworkPacket> PacketGateway bindWriter(Packet packet, PacketWriterConverter<T> converter) {
        return bindWriter(packet.getAliasAsUUID(), converter);
    }

    public <T extends NetworkPacket> PacketGateway bindWriter(String alias, PacketWriterConverter<T> converter) {
        return bindWriter(getAliasAsUUID(alias), converter);
    }

    public <T extends NetworkPacket> PacketGateway bindWriter(UUID alias, PacketWriterConverter<T> converter) {
        writerConverters.put(alias, converter);
        return this;
    }

    public <T extends NetworkPacket> T read(String alias, UUID source, byte[] bytes) {
        return read(getAliasAsUUID(alias), source, bytes);
    }

    public <T extends NetworkPacket> T read(UUID alias, UUID source, byte[] bytes) {
        PacketReaderConverter<T> converter = this.<T>getReader(alias);

        return converter.fromBytes(source, bytes);
    }

    public <T extends NetworkPacket> byte[] write(T packet) {
        PacketWriterConverter<T> converter = this.<T>getWriter(getAliasAsUUID(packet.getAlias()));

        return converter.toBytes(packet);
    }

    @SuppressWarnings("unchecked")
    private <T extends NetworkPacket> PacketReaderConverter<T> cast(PacketReaderConverter<?> converter) {
        try {
            return (PacketReaderConverter<T>) converter;
        } catch (Exception exception) {
            throw new PacketConverterCastException(exception, "Failed to cast converter");
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends NetworkPacket> PacketWriterConverter<T> cast(PacketWriterConverter<?> converter) {
        try {
            return (PacketWriterConverter<T>) converter;
        } catch (Exception exception) {
            throw new PacketConverterCastException(exception, "Failed to cast converter");
        }
    }

    private UUID getAliasAsUUID(String alias) {
        return UUID.nameUUIDFromBytes(alias.getBytes());
    }
}
