package org.tmf.dsmapi.catalog;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.commons.Utilities;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "taxIncludedAmount": "12.00",
 *     "dutyFreeAmount": "10.00",
 *     "taxRate": "20.00",
 *     "currencyCode": "EUR"
 * }
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class Price implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOfferingPrice.class.getName());

    @Column(name = "TAX_INCLUDED_AMOUNT", nullable = true)
    String taxIncludedAmount;

    @Column(name = "DUTY_FREE_AMOUNT", nullable = true)
    String dutyFreeAmount;

    @Column(name = "TAX_RATE", nullable = true)
    String taxRate;

    @Column(name = "CURRENCY_CODE", nullable = true)
    String currencyCode;

    public Price() {
    }

    public String getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(String taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public String getDutyFreeAmount() {
        return dutyFreeAmount;
    }

    public void setDutyFreeAmount(String dutyFreeAmount) {
        this.dutyFreeAmount = dutyFreeAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 53 * hash + (this.taxIncludedAmount != null ? this.taxIncludedAmount.hashCode() : 0);
        hash = 53 * hash + (this.dutyFreeAmount != null ? this.dutyFreeAmount.hashCode() : 0);
        hash = 53 * hash + (this.taxRate != null ? this.taxRate.hashCode() : 0);
        hash = 53 * hash + (this.currencyCode != null ? this.currencyCode.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Price other = (Price) object;
        if (Utilities.areEqual(this.taxIncludedAmount, other.taxIncludedAmount) == false) {
            return false;
        }

        if (Utilities.areEqual(this.dutyFreeAmount, other.dutyFreeAmount) == false) {
            return false;
        }

        if (Utilities.areEqual(this.taxRate, other.taxRate) == false) {
            return false;
        }

        if (Utilities.areEqual(this.currencyCode, other.currencyCode) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Price{" + "taxIncludedAmount=" + taxIncludedAmount + ", dutyFreeAmount=" + dutyFreeAmount + ", taxRate=" + taxRate + ", currencyCode=" + currencyCode + '}';
    }

    public static Price createProto() {
        Price price = new Price();

        price.taxIncludedAmount = "tax included amount";
        price.dutyFreeAmount = "duty free amount";
        price.taxRate = "tax rate";
        price.currencyCode = "currency code";

        return price;
    }

}
