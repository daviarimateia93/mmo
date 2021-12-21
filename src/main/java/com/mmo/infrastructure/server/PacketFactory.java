package com.mmo.infrastructure.server;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PacketFactory {

    private static PacketFactory instance;
    private final Map<UUID, PacketBinaryBuilder<? extends Packet>> builders = new LinkedHashMap<>();

    public static PacketFactory getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PacketFactory();
        }

        return instance;
    }

    private PacketFactory() {

    }

    public <T extends Packet> PacketBinaryBuilder<T> getBuilder(UUID alias)
            throws PacketBuilderCastException, PacketBuilderNotBindedException {

        return Optional.ofNullable(builders.get(alias))
                .map(builder -> this.<T>cast(builder))
                .orElseThrow(
                        () -> new PacketBuilderNotBindedException("PacketBuilder not binded for alias %s", alias));
    }

    public <T extends Packet> PacketFactory bind(Packet packet, PacketBinaryBuilder<T> builder) {
        return bind(packet.getAliasAsUUID(), builder);
    }

    public <T extends Packet> PacketFactory bind(String alias, PacketBinaryBuilder<T> builder) {
        return bind(getAliasAsUUID(alias), builder);
    }

    public <T extends Packet> PacketFactory bind(UUID alias, PacketBinaryBuilder<T> builder) {
        builders.put(alias, builder);
        return this;
    }

    public <T extends Packet> T getPacket(String alias, UUID source, byte[] bytes) {
        return getPacket(getAliasAsUUID(alias), source, bytes);
    }

    public <T extends Packet> T getPacket(UUID alias, UUID source, byte[] bytes) {
        PacketBinaryBuilder<T> builder = this.<T>getBuilder(alias);

        return builder.build(source, bytes);
    }

    @SuppressWarnings("unchecked")
    private <T extends Packet> PacketBinaryBuilder<T> cast(PacketBinaryBuilder<?> builder) {
        try {
            return (PacketBinaryBuilder<T>) builder;
        } catch (Exception exception) {
            throw new PacketBuilderCastException(exception, "Failed to cast builder");
        }
    }

    private UUID getAliasAsUUID(String alias) {
        return UUID.nameUUIDFromBytes(alias.getBytes());
    }
}
