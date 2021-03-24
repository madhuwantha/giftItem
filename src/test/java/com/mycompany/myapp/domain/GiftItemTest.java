package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class GiftItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiftItem.class);
        GiftItem giftItem1 = new GiftItem();
        giftItem1.setId(1L);
        GiftItem giftItem2 = new GiftItem();
        giftItem2.setId(giftItem1.getId());
        assertThat(giftItem1).isEqualTo(giftItem2);
        giftItem2.setId(2L);
        assertThat(giftItem1).isNotEqualTo(giftItem2);
        giftItem1.setId(null);
        assertThat(giftItem1).isNotEqualTo(giftItem2);
    }
}
