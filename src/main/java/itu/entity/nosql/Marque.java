package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marque")
public class Marque {
        @Id
        private String _id;
        @Indexed(unique=true)
        private String marque;

        public String get_id() {
                return _id;
        }

        public void set_id(String _id) {
                this._id = _id;
        }

        public String getMarque() {
                return marque;
        }

        public void setMarque(String marque) throws Exception {
                if(marque == null || marque == "")throw new Exception("Marque requis.");
                this.marque = marque;
        }
}
