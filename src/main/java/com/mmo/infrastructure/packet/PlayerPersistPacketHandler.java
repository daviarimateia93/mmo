package com.mmo.infrastructure.packet;

import java.util.Optional;
import java.util.UUID;

import com.mmo.core.attribute.Attributes;
import com.mmo.core.map.Map;
import com.mmo.core.map.Position;
import com.mmo.core.packet.PacketHandler;
import com.mmo.core.packet.PlayerPersistPacket;
import com.mmo.core.player.Player;
import com.mmo.core.player.PlayerRepository;
import com.mmo.core.stat.Stats;
import com.mmo.infrastructure.mongo.MongoFactory;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

public class PlayerPersistPacketHandler implements PlayerRepository, PacketHandler<PlayerPersistPacket> {

    private final MongoCollection<PlayerEntity> collection;

    public PlayerPersistPacketHandler() {
        collection = MongoFactory.getInstance().getCollection("Player", PlayerEntity.class);
    }

    @Override
    public Optional<Player> find(UUID id) {
        PlayerEntity entity = collection.find(Filters.eq("_id", id)).first();

        return Optional.ofNullable(entity)
                .map(PlayerEntity::toPlayer);
    }

    @Override
    public void persist(Player player) {
        PlayerEntity entity = PlayerEntity.of(player);

        collection.replaceOne(
                Filters.eq("_id", entity.getId()),
                entity, new ReplaceOptions().upsert(true));
    }

    @Override
    public void handle(Map map, PlayerPersistPacket packet) {
        persist(packet.getPlayer());
    }

    @Data
    public static class PlayerEntity {
        private UUID id;
        private UUID instanceId;
        private String name;
        private PositionEntity position;
        private StatsEntity stats;
        private AttributesEntity attributes;

        Player toPlayer() {
            return Player.builder()
                    .instanceId(getInstanceId())
                    .name(getName())
                    .position(getPosition().toPosition())
                    .stats(getStats().toStats())
                    .attributes(getAttributes().toAttributes())
                    .build();
        }

        static PlayerEntity of(Player player) {
            PlayerEntity entity = new PlayerEntity();
            entity.setId(player.getId());
            entity.setInstanceId(player.getInstanceId());
            entity.setName(player.getName());
            entity.setPosition(PositionEntity.of(player.getPosition()));
            entity.setStats(StatsEntity.of(player.getStats()));
            entity.setAttributes(AttributesEntity.of(player.getAttributes()));

            return entity;
        }
    }

    @Data
    public static class PositionEntity {
        private Long x;
        private Long y;
        private Long z;

        static PositionEntity of(Position position) {
            PositionEntity entity = new PositionEntity();
            entity.setX(position.getX());
            entity.setY(position.getY());
            entity.setZ(position.getZ());

            return entity;
        }

        Position toPosition() {
            return Position.builder()
                    .x(getX())
                    .y(getY())
                    .z(getZ())
                    .build();
        }
    }

    @Data
    public static class StatsEntity {
        private Integer strength;
        private Integer dexterity;
        private Integer intelligence;
        private Integer concentration;
        private Integer sense;
        private Integer charm;

        static StatsEntity of(Stats stats) {
            StatsEntity entity = new StatsEntity();
            entity.setStrength(stats.getStrength());
            entity.setDexterity(stats.getDexterity());
            entity.setIntelligence(stats.getIntelligence());
            entity.setConcentration(stats.getConcentration());
            entity.setSense(stats.getSense());
            entity.setCharm(stats.getCharm());

            return entity;
        }

        Stats toStats() {
            return Stats.builder()
                    .strength(getStrength())
                    .dexterity(getDexterity())
                    .intelligence(getIntelligence())
                    .concentration(getConcentration())
                    .sense(getSense())
                    .charm(getCharm())
                    .build();
        }
    }

    @Data
    public static class AttributesEntity {
        @Setter(AccessLevel.NONE)
        private Integer hp;
        @Setter(AccessLevel.NONE)
        private Integer mp;
        private Integer attack;
        private Integer defense;
        private Integer magicDefense;
        private Integer hitRate;
        private Integer critical;
        private Integer dodgeRate;
        private Integer attackSpeed;
        private Integer moveSpeed;
        @Setter(AccessLevel.NONE)
        private Integer hpRecovery;
        @Setter(AccessLevel.NONE)
        private Integer mpRecovery;
        private Integer attackRange;

        public void setHP(Integer hp) {
            this.hp = hp;
        }

        public Integer getHP() {
            return hp;
        }

        public void setMP(Integer mp) {
            this.mp = mp;
        }

        public Integer getMP() {
            return mp;
        }

        public void setHPRecovery(Integer hpRecovery) {
            this.hpRecovery = hpRecovery;
        }

        public Integer getHPRecovery() {
            return hpRecovery;
        }

        public void setMPRecovery(Integer mpRecovery) {
            this.mpRecovery = mpRecovery;
        }

        public Integer getMPRecovery() {
            return mpRecovery;
        }

        static AttributesEntity of(Attributes stats) {
            AttributesEntity entity = new AttributesEntity();
            entity.setHP(stats.getHP());
            entity.setMP(stats.getMP());
            entity.setAttack(stats.getAttack());
            entity.setDefense(stats.getDefense());
            entity.setMagicDefense(stats.getMagicDefense());
            entity.setHitRate(stats.getHitRate());
            entity.setCritical(stats.getCritical());
            entity.setDodgeRate(stats.getDodgeRate());
            entity.setAttackSpeed(stats.getAttackSpeed());
            entity.setMoveSpeed(stats.getMoveSpeed());
            entity.setHPRecovery(stats.getHPRecovery());
            entity.setMPRecovery(stats.getMPRecovery());
            entity.setAttackRange(stats.getAttackRange());

            return entity;
        }

        Attributes toAttributes() {
            return Attributes.builder()
                    .hp(getHP())
                    .mp(getMP())
                    .attack(getAttack())
                    .defense(getDefense())
                    .magicDefense(getMagicDefense())
                    .hitRate(getHitRate())
                    .critical(getCritical())
                    .dodgeRate(getDodgeRate())
                    .attackSpeed(getAttackSpeed())
                    .moveSpeed(getMoveSpeed())
                    .hpRecovery(getHPRecovery())
                    .mpRecovery(getMPRecovery())
                    .attackRange(
                            getAttackRange())
                    .build();
        }
    }
}
