package matcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private String productName;
    private String manufacturer;
    private String model;
    private String family;
    private String announceDate;

    public String getProductName() {
        return productName;
    }

    @JsonProperty("product_name")
    public void setProductName(String name) {
        this.productName = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer != null) {
            this.manufacturer = manufacturer.trim().toUpperCase();
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAnnounceDate() {
        return announceDate;
    }

    @JsonProperty("announced-date")
    public void setAnnounceDate(String announceDate) {
        this.announceDate = announceDate;
    }

}
