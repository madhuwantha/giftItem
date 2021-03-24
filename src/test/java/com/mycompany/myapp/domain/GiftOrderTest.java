package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class GiftOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiftOrder.class);
        GiftOrder giftOrder1 = new GiftOrder();
        giftOrder1.setId(1L);
        GiftOrder giftOrder2 = new GiftOrder();
        giftOrder2.setId(giftOrder1.getId());
        assertThat(giftOrder1).isEqualTo(giftOrder2);
        giftOrder2.setId(2L);
        assertThat(giftOrder1).isNotEqualTo(giftOrder2);
        giftOrder1.setId(null);
        assertThat(giftOrder1).isNotEqualTo(giftOrder2);
    }
}
