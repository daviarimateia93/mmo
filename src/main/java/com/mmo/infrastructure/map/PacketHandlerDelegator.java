package com.mmo.infrastructure.map;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.mmo.core.map.Map;
import com.mmo.infrastructure.map.packet.AttackPacket;
import com.mmo.infrastructure.map.packet.AttackPacketHandler;
import com.mmo.infrastructure.server.Packet;

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
        bind(AttackPacket.class, new AttackPacketHandler());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void delegate(Map map, Packet packet) {
        PacketHandler handler = Optional.ofNullable(handlers.get(packet.getClass()))
                .orElseThrow(
                        () -> new PacketHandlerNotBindedException("There is no packet handler for packet %s", packet));

        handler.handle(map, packet);
    }

    private void bind(Class<? extends Packet> type, PacketHandler<?> handler) {
        handlers.put(type, handler);
    }
}
