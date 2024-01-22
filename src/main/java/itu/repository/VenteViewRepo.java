package itu.repository;

import itu.entity.sql.VenteView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VenteViewRepo extends JpaRepository<VenteView, Integer> {
    @Query(value = "select mois.mois as id, mois.mois as month, coalesce(commission, 0) as commission, mois.designation from mois left outer join (select month,sum(commission) as commission from (select * from v_vente where year = :year) as c group by month) as d on mois.mois = d.month", nativeQuery = true)
    List<VenteView> findAllByYear(@Param("year") int year);
}
