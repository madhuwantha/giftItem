package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Order entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select distinct order from Order order left join fetch order.giftItems",
        countQuery = "select count(distinct order) from Order order")
    Page<Order> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct order from Order order left join fetch order.giftItems")
    List<Order> findAllWithEagerRelationships();

    @Query("select order from Order order left join fetch order.giftItems where order.id =:id")
    Optional<Order> findOneWithEagerRelationships(@Param("id") Long id);
}
