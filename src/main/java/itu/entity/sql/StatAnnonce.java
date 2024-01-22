package itu.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "statannonce")
@Getter
@Setter
public class StatAnnonce {
    @Id
    private Integer mois;
    String designation;
    double total;

    public static double somme(List<StatAnnonce> s){
        double ret = 0;
        for(StatAnnonce stat : s){
            ret += stat.total;
        }

        return ret;
    }
}
