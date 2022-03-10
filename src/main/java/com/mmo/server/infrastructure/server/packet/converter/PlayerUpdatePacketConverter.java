package com.mmo.server.infrastructure.server.packet.converter;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.packet.PlayerUpdatePacket;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.infrastructure.animate.AnimateDTO;
import com.mmo.server.infrastructure.animate.AttributesDTO;
import com.mmo.server.infrastructure.animate.StatsDTO;
import com.mmo.server.infrastructure.map.PositionDTO;
import com.mmo.server.infrastructure.server.packet.PacketConverter;
import com.mmo.server.infrastructure.server.packet.PacketReader;
import com.mmo.server.infrastructure.server.packet.PacketWriter;

public class PlayerUpdatePacketConverter implements PacketConverter<PlayerUpdatePacket> {

    @Override
    public PlayerUpdatePacket read(UUID source, OffsetDateTime creation, byte[] bytes) {
        try (PacketReader reader = new PacketReader(bytes)) {
            UUID userId = reader.readUUID();
            String name = reader.readUTF();
            PositionDTO position = PositionConverter.read(reader);
            StatsDTO stats = StatsConverter.read(reader);
            AttributesDTO attributes = AttributesConverter.read(reader);
            AnimateDTO animate = AnimateConverter.read(reader);

            return PlayerUpdatePacket.dtoBuilder()
                    .source(source)
                    .userId(userId)
                    .name(name)
                    .positionX(position.getX())
                    .positionZ(position.getZ())
                    .statsStrength(stats.getStrength())
                    .statsDexterity(stats.getDexterity())
                    .statsIntelligence(stats.getIntelligence())
                    .statsConcentration(stats.getConcentration())
                    .statsSense(stats.getSense())
                    .statsCharm(stats.getCharm())
                    .attributesHP(attributes.getHP())
                    .attributesMP(attributes.getMP())
                    .attributesAttack(attributes.getAttack())
                    .attributesDefense(attributes.getDefense())
                    .attributesMagicDefense(attributes.getMagicDefense())
                    .attributesHitRate(attributes.getHitRate())
                    .attributesCritical(attributes.getCritical())
                    .attributesDodgeRate(attributes.getDodgeRate())
                    .attributesAttackSpeed(attributes.getAttackSpeed())
                    .attributesMoveSpeed(attributes.getMoveSpeed())
                    .attributesHPRecovery(attributes.getHPRecovery())
                    .attributesMPRecovery(attributes.getMPRecovery())
                    .attributesAttackRange(attributes.getAttackRange())
                    .alive(animate.isAlive())
                    .moving(animate.isMoving())
                    .targetPositionX(animate.getTargetPosition().map(PositionDTO::getX).orElse(null))
                    .targetPositionX(animate.getTargetPosition().map(PositionDTO::getZ).orElse(null))
                    .attacking(animate.isAttacking())
                    .targetAnimate(animate.getTargetAnimate().orElse(null))
                    .buildDTO();
        }
    }

    @Override
    public byte[] write(PlayerUpdatePacket packet) {
        try (PacketWriter writer = new PacketWriter()) {
            UUID userId = packet.getUserId();
            String name = packet.getName();
            PositionDTO position = getPosition(packet);
            StatsDTO stats = getStats(packet);
            AttributesDTO attributes = getAttributes(packet);
            AnimateDTO animate = getAnimate(packet);

            writer.writeUUID(userId);
            writer.writeUTF(name);
            PositionConverter.write(writer, position);
            StatsConverter.write(writer, stats);
            AttributesConverter.write(writer, attributes);
            AnimateConverter.write(writer, animate);

            return writer.toBytes();
        }
    }

    private PositionDTO getPosition(PlayerUpdatePacket packet) {
        return PositionDTO.of(Position.builder()
                .x(packet.getPositionX())
                .z(packet.getPositionZ())
                .build());
    }

    private StatsDTO getStats(PlayerUpdatePacket packet) {
        return StatsDTO.of(Stats.builder()
                .strength(packet.getStatsStrength())
                .dexterity(packet.getStatsDexterity())
                .intelligence(packet.getStatsIntelligence())
                .concentration(packet.getStatsConcentration())
                .sense(packet.getStatsSense())
                .charm(packet.getStatsCharm())
                .build());
    }

    private AttributesDTO getAttributes(PlayerUpdatePacket packet) {
        return AttributesDTO.of(Attributes.builder()
                .hp(packet.getAttributesHP())
                .mp(packet.getAttributesMP())
                .attack(packet.getAttributesAttack())
                .defense(packet.getAttributesDefense())
                .magicDefense(packet.getAttributesMagicDefense())
                .hitRate(packet.getAttributesHitRate())
                .critical(packet.getAttributesCritical())
                .dodgeRate(packet.getAttributesDodgeRate())
                .attackSpeed(packet.getAttributesAttackSpeed())
                .moveSpeed(packet.getAttributesMoveSpeed())
                .hpRecovery(packet.getAttributesHPRecovery())
                .mpRecovery(packet.getAttributesMPRecovery())
                .attackRange(packet.getAttributesAttackRange())
                .build());
    }

    private AnimateDTO getAnimate(PlayerUpdatePacket packet) {
        AnimateDTO dto = new AnimateDTO();
        dto.setAlive(packet.isAlive());
        dto.setMoving(packet.isMoving());

        if (packet.getTargetPositionX().isPresent() && packet.getTargetPositionZ().isPresent()) {
            PositionDTO targetPosition = PositionDTO.of(Position.builder()
                    .x(packet.getTargetPositionX().get())
                    .z(packet.getTargetPositionZ().get())
                    .build());

            dto.setTargetPosition(targetPosition);

        }

        dto.setAttacking(packet.isAttacking());

        packet.getTargetAnimate().ifPresent(dto::setTargetAnimate);

        return dto;
    }
}