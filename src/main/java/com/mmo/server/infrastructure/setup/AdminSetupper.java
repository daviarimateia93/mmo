package com.mmo.server.infrastructure.setup;

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

    private static final String CONFIG_ADMIN_USER_ID = "admin.user.id";
    private static final String CONFIG_ADMIN_USER_NAME = "admin.user.name";
    private static final String CONFIG_ADMIN_USER_PASSWORD = "admin.user.password";

    private static final String CONFIG_ADMIN_PLAYER_ID = "admin.player.id";
    private static final String CONFIG_ADMIN_PLAYER_USER_ID = "admin.player.userId";
    private static final String CONFIG_ADMIN_PLAYER_NAME = "admin.player.name";
    private static final String CONFIG_ADMIN_PLAYER_POSITION_X = "admin.player.position.x";
    private static final String CONFIG_ADMIN_PLAYER_POSITION_Z = "admin.player.position.z";
    private static final String CONFIG_ADMIN_PLAYER_STATS_STRENGTH = "admin.player.stats.strength";
    private static final String CONFIG_ADMIN_PLAYER_STATS_DEXTERITY = "admin.player.stats.dexterity";
    private static final String CONFIG_ADMIN_PLAYER_STATS_INTELLIGENCE = "admin.player.stats.intelligence";
    private static final String CONFIG_ADMIN_PLAYER_STATS_CONCENTRATION = "admin.player.stats.concentration";
    private static final String CONFIG_ADMIN_PLAYER_STATS_SENSE = "admin.player.stats.sense";
    private static final String CONFIG_ADMIN_PLAYER_STATS_CHARM = "admin.player.stats.charm";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP = "admin.player.attributes.hp";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP = "admin.player.attributes.mp";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK = "admin.player.attributes.attack";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_DEFENSE = "admin.player.attributes.defense";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MAGIC_DEFENSE = "admin.player.attributes.magicDefense";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HIT_RATE = "admin.player.attributes.hitRate";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_CRITICAL = "admin.player.attributes.critical";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_DODGE_RATE = "admin.player.attributes.dodgeRate";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_SPEED = "admin.player.attributes.attackSpeed";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MOVE_SPEED = "admin.player.attributes.moveSpeed";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP_RECOVERY = "admin.player.attributes.hpRecovery";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP_RECOVERY = "admin.player.attributes.mpRecovery";
    private static final String CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_RANGE = "admin.player.attributes.attackRange";

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
        userRepository.persist(newUser());
        playerRepository.persist(newPlayer());
    }

    private User newUser() {
        return User.builder()
                .id(configProvider.getUUID(CONFIG_ADMIN_USER_ID))
                .name(configProvider.getString(CONFIG_ADMIN_USER_NAME))
                .password(configProvider.getString(CONFIG_ADMIN_USER_PASSWORD))
                .build();
    }

    private Player newPlayer() {
        return Player.builder()
                .instanceId(configProvider.getUUID(CONFIG_ADMIN_PLAYER_ID))
                .userId(configProvider.getUUID(CONFIG_ADMIN_PLAYER_USER_ID))
                .name(configProvider.getString(CONFIG_ADMIN_PLAYER_NAME))
                .position(Position.builder()
                        .x(configProvider.getInt(CONFIG_ADMIN_PLAYER_POSITION_X))
                        .z(configProvider.getInt(CONFIG_ADMIN_PLAYER_POSITION_Z))
                        .build())
                .stats(Stats.builder()
                        .strength(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_STRENGTH))
                        .dexterity(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_DEXTERITY))
                        .intelligence(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_INTELLIGENCE))
                        .concentration(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_CONCENTRATION))
                        .sense(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_SENSE))
                        .charm(configProvider.getInt(CONFIG_ADMIN_PLAYER_STATS_CHARM))
                        .build())
                .attributes(Attributes.builder()
                        .hp(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP))
                        .mp(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP))
                        .attack(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK))
                        .defense(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_DEFENSE))
                        .magicDefense(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MAGIC_DEFENSE))
                        .hitRate(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HIT_RATE))
                        .critical(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_CRITICAL))
                        .dodgeRate(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_DODGE_RATE))
                        .attackSpeed(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_SPEED))
                        .moveSpeed(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MOVE_SPEED))
                        .hpRecovery(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_HP_RECOVERY))
                        .mpRecovery(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_MP_RECOVERY))
                        .attackRange(configProvider.getInt(CONFIG_ADMIN_PLAYER_ATTRIBUTES_ATTACK_RANGE))
                        .build())
                .build();
    }
}
