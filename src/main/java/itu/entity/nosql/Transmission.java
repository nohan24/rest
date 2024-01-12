package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transmission")
public class Transmission {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) throws Exception {
        if(transmission == "" || transmission == null)throw new Exception("Ce champ est requis.");
        this.transmission = transmission;
    }

    @Id
    private String _id;
    @Indexed(unique=true)
    private String transmission;
}
