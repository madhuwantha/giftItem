package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GiftOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GiftOrder entity.
 */
@Repository
public interface GiftOrderRepository extends JpaRepository<GiftOrder, Long> {

    @Query(value = "select distinct giftOrder from GiftOrder giftOrder left join fetch giftOrder.giftItems",
        countQuery = "select count(distinct giftOrder) from GiftOrder giftOrder")
    Page<GiftOrder> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct giftOrder from GiftOrder giftOrder left join fetch giftOrder.giftItems")
    List<GiftOrder> findAllWithEagerRelationships();

    @Query("select giftOrder from GiftOrder giftOrder left join fetch giftOrder.giftItems where giftOrder.id =:id")
    Optional<GiftOrder> findOneWithEagerRelationships(@Param("id") Long id);
}
