package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categorie")
public class Categorie {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) throws Exception {
        if(categorie == "" || categorie == null){
            throw new Exception("Catégorie requis.");
        }
        this.categorie = categorie;
    }

    @Id
    private String _id;
    @Indexed(unique=true)
    private String categorie;
}
