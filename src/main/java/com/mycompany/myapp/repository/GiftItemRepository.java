package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GiftItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GiftItem entity.
 */
@Repository
public interface GiftItemRepository extends JpaRepository<GiftItem, Long> {

    @Query(value = "select distinct giftItem from GiftItem giftItem left join fetch giftItem.carts",
        countQuery = "select count(distinct giftItem) from GiftItem giftItem")
    Page<GiftItem> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct giftItem from GiftItem giftItem left join fetch giftItem.carts")
    List<GiftItem> findAllWithEagerRelationships();

    @Query("select giftItem from GiftItem giftItem left join fetch giftItem.carts where giftItem.id =:id")
    Optional<GiftItem> findOneWithEagerRelationships(@Param("id") Long id);
}
