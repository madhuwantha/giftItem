package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class IinventoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Iinventory.class);
        Iinventory iinventory1 = new Iinventory();
        iinventory1.setId(1L);
        Iinventory iinventory2 = new Iinventory();
        iinventory2.setId(iinventory1.getId());
        assertThat(iinventory1).isEqualTo(iinventory2);
        iinventory2.setId(2L);
        assertThat(iinventory1).isNotEqualTo(iinventory2);
        iinventory1.setId(null);
        assertThat(iinventory1).isNotEqualTo(iinventory2);
    }
}
