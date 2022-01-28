package com.mmo.server.infrastructure.setup;

import java.util.UUID;
import java.util.stream.IntStream;

import com.mmo.server.core.attribute.Attributes;
import com.mmo.server.core.map.Position;
import com.mmo.server.core.player.Player;
import com.mmo.server.core.player.PlayerRepository;
import com.mmo.server.core.stat.Stats;
import com.mmo.server.core.user.User;
import com.mmo.server.core.user.UserRepository;
import com.mmo.server.infrastructure.config.ConfigProvider;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class AdminSetupper {

    private static final String CONFIG_ADMIN_COUNT = "admin.count";

    private static final String CONFIG_ADMIN_USER_ID = "admin[%d].user.id";
    private static final String CONFIG_ADMIN_USER_NAME = "admin[%d].user.name";
    private static final String CONFIG_ADMIN_USER_PASSWORD = "admin[%d].user.password";

    private static final String CONFIG_ADMIN_PLAYER_ID = "admin[%d].player.id";
    private static final String CONFIG_ADMIN_PLAYER_USER_ID = "admin[%d].player.userId";
    private static final String CONFIG_ADMIN_PLAYER_NAME = "admin[%d].player.name";
    private static final String CONFIG_ADMIN_PLAYER_POSITION_X = "admin[%d].player.position.x";
    private static final String CONFIG_ADMIN_PLAYER_POSITION_Z = "admin[%d].player.position.z";
    private static final String CONFIG_ADMIN_PLAYER_STATS_STRENGTH = "admin[%d].player.stats.strength";
    private static final String CONFIG_ADMIN_PLAYER_STATS_DEXTERITY = "admin[%d].player.stats.dexterity";
    private static final String CONFIG_ADMIN_PLAYER_STATS_INTELLIGENCE = "admin[%d].player.stats.intelligence";
    private static final String CONFIG_ADMIN_PLAYER_STATS_CONCENTRATION = "admin[%d].player.stats.concentration";
    private static final String CONFIG_ADMIN_PLAYER_STATS_SENSE = "admin[%d].player.stats.sense";
    private static final String CONFIG_ADMIN_PLAYER_STATS_CHARM = "admin[%d].player.stats.charm";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP = "admin[%d].player.attributes.hp";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP = "admin[%d].player.attributes.mp";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK = "admin[%d].player.attributes.attack";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_DEFENSE = "admin[%d].player.attributes.defense";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MAGIC_DEFENSE = "admin[%d].player.attributes.magicDefense";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HIT_RATE = "admin[%d].player.attributes.hitRate";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_CRITICAL = "admin[%d].player.attributes.critical";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_DODGE_RATE = "admin[%d].player.attributes.dodgeRate";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_SPEED = "admin[%d].player.attributes.attackSpeed";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MOVE_SPEED = "admin[%d].player.attributes.moveSpeed";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP_RECOVERY = "admin[%d].player.attributes.hpRecovery";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP_RECOVERY = "admin[%d].player.attributes.mpRecovery";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_RANGE = "admin[%d].player.attributes.attackRange";

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ConfigProvider configProvider;

    @Builder
    private AdminSetupper(
            @NonNull UserRepository userRepository,
            @NonNull PlayerRepository playerRepository) {

        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.configProvider = ConfigProvider.getInstance();
    }

    public void setup() {
        IntStream.range(1, configProvider.getInt(CONFIG_ADMIN_COUNT) + 1)
                .forEach(index -> {
                    userRepository.persist(newUser(index));
                    playerRepository.persist(newPlayer(index));
                });
    }

    private User newUser(int index) {
        return User.builder()
                .id(getConfigUUID(CONFIG_ADMIN_USER_ID, index))
                .name(getConfigString(CONFIG_ADMIN_USER_NAME, index))
                .password(getConfigString(CONFIG_ADMIN_USER_PASSWORD, index))
                .build();
    }

    private Player newPlayer(int index) {
        return Player.builder()
                .instanceId(getConfigUUID(CONFIG_ADMIN_PLAYER_ID, index))
                .userId(getConfigUUID(CONFIG_ADMIN_PLAYER_USER_ID, index))
                .name(getConfigString(CONFIG_ADMIN_PLAYER_NAME, index))
                .position(Position.builder()
                        .x(getConfigInt(CONFIG_ADMIN_PLAYER_POSITION_X, index))
                        .z(getConfigInt(CONFIG_ADMIN_PLAYER_POSITION_Z, index))
                        .build())
                .stats(Stats.builder()
                        .strength(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_STRENGTH, index))
                        .dexterity(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_DEXTERITY, index))
                        .intelligence(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_INTELLIGENCE, index))
                        .concentration(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_CONCENTRATION, index))
                        .sense(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_SENSE, index))
                        .charm(getConfigInt(CONFIG_ADMIN_PLAYER_STATS_CHARM, index))
                        .build())
                .attributes(Attributes.builder()
                        .hp(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP, index))
                        .mp(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP, index))
                        .attack(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK, index))
                        .defense(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_DEFENSE, index))
                        .magicDefense(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MAGIC_DEFENSE, index))
                        .hitRate(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HIT_RATE, index))
                        .critical(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_CRITICAL, index))
                        .dodgeRate(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_DODGE_RATE, index))
                        .attackSpeed(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_SPEED, index))
                        .moveSpeed(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MOVE_SPEED, index))
                        .hpRecovery(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP_RECOVERY, index))
                        .mpRecovery(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP_RECOVERY, index))
                        .attackRange(getConfigInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_RANGE, index))
                        .build())
                .build();
    }

    private String getConfigString(String format, int index) {
        return configProvider.getString(resolveConfigName(format, index));
    }

    private UUID getConfigUUID(String format, int index) {
        return configProvider.getUUID(resolveConfigName(format, index));
    }

    private int getConfigInt(String format, int index) {
        return configProvider.getInt(resolveConfigName(format, index));
    }

    private String resolveConfigName(String format, Object... arguments) {
        return String.format(format, arguments);
    }
}
