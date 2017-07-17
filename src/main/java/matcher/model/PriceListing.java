package matcher.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PriceListing {

    private String title;
    private String manufacturer;
    private String currency;
    private BigDecimal price;

    // Used to validate algorithm only matches a price listing once.
    private boolean matched = false;

    public PriceListing() {
    }
    
    public PriceListing(final String title, final String manu, final float price, final String currency) {
        this.setTitle(title);
        this.setManufacturer(manu);
        this.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        this.setCurrency(currency);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Trims and uppercases the manufacturer String
     * 
     * @param manufacturer
     */
    public void setManufacturer(String manufacturer) {
        if (manufacturer != null) {
            // clean the manufacturer and uppercase for key consistency
            this.manufacturer = manufacturer.trim().toUpperCase();
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Hack to get this as a String for the JSON output.
     * 
     * @return
     */
    public String getPrice() {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonIgnore
    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

}
