package com.kalacheng.message.bean;

public class MsgShopOrderBean {

    public MsgShopOrderBean() {

    }

    private int msgType;
    private int orderStatus;//订单状态 1 待卖家发货；2 待买家收货；3 申请退款待审核；4 申请退款审核失败；5 待买家发货；6 待卖家收货；7 退款完成；
    private long orderId;//订单id
    private long buyerId;//买家id
    private long sellerId;//卖家id

    private String productName;//商品名称
    private String productImage;//商品图片
    private int productNum;//商品数量
    private double totalAmount;//总金额

    private String name;//收货人姓名
    private String phoneNum;//收货人电话
    private String address;//收货人地址

    private int refundType;//退款类型 1 仅退款；2 退货退款
    private String refundNotes;//申请退款 备注
    private String refundNotesImages;//申请退款 图片

    private String reason;//审核不通过原因

    private String refundTime;//退款到账时间

    private String tips;//黄色提示语

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRefundType() {
        return refundType;
    }

    public void setRefundType(int refundType) {
        this.refundType = refundType;
    }

    public String getRefundNotes() {
        return refundNotes;
    }

    public void setRefundNotes(String refundNotes) {
        this.refundNotes = refundNotes;
    }

    public String getRefundNotesImages() {
        return refundNotesImages;
    }

    public void setRefundNotesImages(String refundNotesImages) {
        this.refundNotesImages = refundNotesImages;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
}
