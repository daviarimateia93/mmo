package com.mmo.core.stat;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.mmo.core.looper.LooperContextMocker;
import com.mmo.core.property.PropertyModifierAction;

public class StatsTest {

    @Test
    public void modify() throws InterruptedException {
        Stats stats = Stats.builder()
                .strength(33)
                .dexterity(10)
                .intelligence(11)
                .concentration(12)
                .sense(13)
                .charm(14)
                .build();

        StatModifier incrementStr15 = StatModifier.builder()
                .action(PropertyModifierAction.INCREMENT)
                .stat(Stat.STRENGTH)
                .value(15)
                .persisted(true)
                .build();

        StatModifier decrementStr5 = StatModifier.builder()
                .action(PropertyModifierAction.DECREMENT)
                .stat(Stat.STRENGTH)
                .value(5)
                .expiration(OffsetDateTime.now().plusSeconds(2))
                .persisted(true)
                .build();

        stats.modify(incrementStr15);
        stats.modify(decrementStr5);

        assertThat(stats.getStrength(), equalTo(33));
        assertThat(stats.getFinalStrength(), equalTo(43));

        LooperContextMocker.update(stats, 5000);

        assertThat(stats.getFinalStrength(), equalTo(48));
        assertThat(stats.getDexterity(), equalTo(10));
        assertThat(stats.getFinalDexterity(), equalTo(10));
        assertThat(stats.getIntelligence(), equalTo(11));
        assertThat(stats.getFinalIntelligence(), equalTo(11));
        assertThat(stats.getConcentration(), equalTo(12));
        assertThat(stats.getFinalConcentration(), equalTo(12));
        assertThat(stats.getSense(), equalTo(13));
        assertThat(stats.getFinalSense(), equalTo(13));
        assertThat(stats.getCharm(), equalTo(14));
        assertThat(stats.getFinalCharm(), equalTo(14));
    }
}
