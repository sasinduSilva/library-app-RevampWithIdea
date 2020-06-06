package com.library.app.common.model.filter;

public class PaginationData {
    private int firstResult;
    private int maxResults;
    private String orderFields;
    private OrderMode orderMode;

    public enum OrderMode{
        ASCENDING, DESCENDIG
    }

    public PaginationData(int firstResult, int maxResults, String orderFields, OrderMode orderMode) {
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.orderFields = orderFields;
        this.orderMode = orderMode;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public String getOrderFields() {
        return orderFields;
    }

    public OrderMode getOrderMode() {
        return orderMode;
    }

    public boolean isAscending(){
        return OrderMode.ASCENDING.equals(orderMode);
    }

    @Override
    public String toString() {
        return "PaginationData{" +
                "firstResult=" + firstResult +
                ", maxResults=" + maxResults +
                ", orderFields='" + orderFields + '\'' +
                ", orderMode=" + orderMode +
                '}';
    }
}
