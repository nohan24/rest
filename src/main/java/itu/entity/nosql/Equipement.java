package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "equipement")
public class Equipement {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) throws Exception {
        if(designation == null ||designation == "")throw new Exception("Le champ est requis.");
        this.designation = designation;
    }

    @Id
    String _id;
    @Indexed(unique=true)
    String designation;
}
