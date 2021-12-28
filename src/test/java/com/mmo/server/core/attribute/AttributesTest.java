package com.mmo.server.core.attribute;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.mmo.server.core.looper.LooperContextMocker;
import com.mmo.server.core.property.PropertyModifierAction;

public class AttributesTest {

    @Test
    public void modify() throws InterruptedException {
        Attributes attributes = Attributes.builder()
                .hp(33)
                .mp(10)
                .attack(11)
                .defense(12)
                .magicDefense(13)
                .hitRate(14)
                .critical(15)
                .dodgeRate(16)
                .attackSpeed(17)
                .moveSpeed(18)
                .hpRecovery(19)
                .mpRecovery(20)
                .attackRange(21)
                .build();

        AttributeModifier incrementHP15 = AttributeModifier.builder()
                .action(PropertyModifierAction.INCREMENT)
                .attribute(Attribute.HP)
                .value(15)
                .persisted(true)
                .build();

        AttributeModifier decrementHP5 = AttributeModifier.builder()
                .action(PropertyModifierAction.DECREMENT)
                .attribute(Attribute.HP)
                .value(5)
                .expiration(OffsetDateTime.now().plusSeconds(2))
                .persisted(true)
                .build();

        attributes.modify(incrementHP15);
        attributes.modify(decrementHP5);

        assertThat(attributes.getHP(), equalTo(33));
        assertThat(attributes.getFinalHP(), equalTo(43));

        LooperContextMocker.update(attributes, 5000);

        assertThat(attributes.getFinalHP(), equalTo(48));
        assertThat(attributes.getMP(), equalTo(10));
        assertThat(attributes.getFinalMP(), equalTo(10));
        assertThat(attributes.getAttack(), equalTo(11));
        assertThat(attributes.getFinalAttack(), equalTo(11));
        assertThat(attributes.getDefense(), equalTo(12));
        assertThat(attributes.getFinalDefense(), equalTo(12));
        assertThat(attributes.getMagicDefense(), equalTo(13));
        assertThat(attributes.getFinalMagicDefense(), equalTo(13));
        assertThat(attributes.getHitRate(), equalTo(14));
        assertThat(attributes.getFinalHitRate(), equalTo(14));
        assertThat(attributes.getCritical(), equalTo(15));
        assertThat(attributes.getFinalCritical(), equalTo(15));
        assertThat(attributes.getDodgeRate(), equalTo(16));
        assertThat(attributes.getFinalDodgeRate(), equalTo(16));
        assertThat(attributes.getAttackSpeed(), equalTo(17));
        assertThat(attributes.getFinalAttackSpeed(), equalTo(17));
        assertThat(attributes.getMoveSpeed(), equalTo(18));
        assertThat(attributes.getFinalMoveSpeed(), equalTo(18));
        assertThat(attributes.getHPRecovery(), equalTo(19));
        assertThat(attributes.getFinalHPRecovery(), equalTo(19));
        assertThat(attributes.getMPRecovery(), equalTo(20));
        assertThat(attributes.getFinalMPRecovery(), equalTo(20));
        assertThat(attributes.getAttackRange(), equalTo(21));
        assertThat(attributes.getFinalAttackRange(), equalTo(21));
    }
}
