package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "detail")
public class Annonce extends Detail{
    @Id
    private String _id;
    private Detailelectrique detailelectrique;
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Detailelectrique getDetailelectrique() {
        return detailelectrique;
    }

    public void setDetailelectrique(Detailelectrique detailelectrique) {
        this.detailelectrique = detailelectrique;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
