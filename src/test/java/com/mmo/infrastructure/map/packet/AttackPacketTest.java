package com.mmo.infrastructure.map.packet;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class AttackPacketTest {

    @Test
    public void serializeAndDeserialize() {
        UUID source = UUID.randomUUID();
        UUID target = UUID.randomUUID();

        AttackPacket expected = new AttackPacket(source, target);

        AttackPacket result = AttackPacket.builder()
                .build(source, expected.toBytes());

        assertThat(result, equalTo(expected));
        assertThat(result.getAlias(), equalTo("ATTACK"));
    }
}
