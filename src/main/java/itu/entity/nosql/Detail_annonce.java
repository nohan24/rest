package itu.entity.nosql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "detail")
@Getter
@Setter
public class Detail_annonce extends Detail{
    @Id
    private String _id;
    private Detailelectrique detailelectrique;
    private List<String> images;

}
