package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GiftItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GiftItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftItemRepository extends JpaRepository<GiftItem, Long> {
}
