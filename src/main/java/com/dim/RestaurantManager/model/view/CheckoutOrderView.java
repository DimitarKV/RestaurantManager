package com.dim.RestaurantManager.model.view;

public class CheckoutOrderView {
    private Long orderId;
    private String name;
    private Double price;
    private String payer;
    private Boolean checkDisabled;
    private Boolean checked;
    private String imageUrl;

    public Long getOrderId() {
        return orderId;
    }

    public CheckoutOrderView setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getName() {
        return name;
    }

    public CheckoutOrderView setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public CheckoutOrderView setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getPayer() {
        return payer;
    }

    public CheckoutOrderView setPayer(String payer) {
        this.payer = payer;
        return this;
    }

    public Boolean getCheckDisabled() {
        return checkDisabled;
    }

    public CheckoutOrderView setCheckDisabled(Boolean checkDisabled) {
        this.checkDisabled = checkDisabled;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CheckoutOrderView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Boolean getChecked() {
        return checked;
    }

    public CheckoutOrderView setChecked(Boolean checked) {
        this.checked = checked;
        return this;
    }
}
