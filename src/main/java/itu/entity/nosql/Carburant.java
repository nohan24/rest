package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carburant")
public class Carburant {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) throws Exception {
        if(carburant == null || carburant == "")throw new Exception("Le champ carburant est requis.");
        this.carburant = carburant;
    }

    @Id
    private String _id;
    @Indexed(unique=true)
    private String carburant;
}
