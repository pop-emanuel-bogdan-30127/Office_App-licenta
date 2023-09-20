package net.officeweb.backend.repositories;

import net.officeweb.backend.entities.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, Long> {

    List<OfficeEntity> findByCustomerAccountId(Long id);

    @Transactional
    void deleteByCustomerAccountId(long id);

    @Query(value = "SELECT * FROM hotel  WHERE hotel.available_from >= ?1 AND hotel.available_to <= ?2 AND hotel.ID NOT IN " +
            "(SELECT hotel_id FROM reservation WHERE (check_in >= ?1 OR check_out <= ?2))", nativeQuery = true)
    List<OfficeEntity> findAllBetweenDates(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);
}
