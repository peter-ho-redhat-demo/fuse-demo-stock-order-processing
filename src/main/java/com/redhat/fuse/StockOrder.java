package com.redhat.fuse;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

// Example data: 10A9PaulineMISINXD12345678BUYShare2500.45USD01-08-2009
// Example data: 10B2StephenHISINHK23232323SELShare1400.23USD02-08-2009
// Example data: 10A4VincentCISINJP12341234BUYShare0500.00USD03-08-2009

@FixedLengthRecord(length=54, paddingChar=' ')
public class StockOrder {

    @DataField(pos = 1, length=2)
    private int orderNr;

    @DataField(pos = 3, length=2)
    private String clientNr;

    @DataField(pos = 5, length=7)
    private String firstName;

    @DataField(pos = 12, length=1, align="L")
    private String lastName;

    @DataField(pos = 13, length=4)
    private String instrumentCode;

    @DataField(pos = 17, length=2)
    private String instrumentCountryCode;

    @DataField(pos = 19, length=8)
    private String instrumentNumber;

    @DataField(pos = 27, length=3)
    private String orderType;

    @DataField(pos = 30, length=5)
    private String instrumentType;

    @DataField(pos = 35, precision = 2, length=7)
    private BigDecimal amount;

    @DataField(pos = 42, length=3)
    private String currency;

    @DataField(pos = 45, length=10, pattern = "dd-MM-yyyy")
    private Date orderDate;

    private int randomAwardPoints;

    private String clientMembership;

    public String getClientMembership() {
        return clientMembership;
    }

    public void setClientMembership(String clientMembership) {
        this.clientMembership = clientMembership;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public String getClientNr() {
        return clientNr;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public String getInstrumentNumber() {
        return instrumentNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getInstrumentCountryCode() {
        return instrumentCountryCode;
    }

    public int getRandomAwardPoints() {
        return randomAwardPoints;
    }

    public void setRandomAwardPoints(int randomAwardPoints) {
        this.randomAwardPoints = randomAwardPoints;
    }

}