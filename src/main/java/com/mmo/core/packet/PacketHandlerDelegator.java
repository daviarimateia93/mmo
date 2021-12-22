package com.mmo.core.packet;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.mmo.core.map.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class PacketHandlerDelegator {

    private static PacketHandlerDelegator instance;

    private final ConcurrentHashMap<Class<? extends Packet>, PacketHandler<?>> handlers = new ConcurrentHashMap<>();

    public static PacketHandlerDelegator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PacketHandlerDelegator();
        }

        return instance;
    }

    private PacketHandlerDelegator() {

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void delegate(Map map, Packet packet) throws PacketHandlerNotBindedException {
        PacketHandler handler = get(packet).orElseThrow(
                () -> new PacketHandlerNotBindedException("There is no packet handler for packet %s", packet));

        handler.handle(map, packet);
    }

    private Optional<PacketHandler<?>> get(Packet packet) {
        return Optional.ofNullable(handlers.get(packet.getClass()));
    }

    public <T extends Packet> void bind(Class<T> type, PacketHandler<T> handler) {
        handlers.put(type, handler);
    }
}
