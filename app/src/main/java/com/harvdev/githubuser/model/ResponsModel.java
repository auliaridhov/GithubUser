package com.harvdev.githubuser.model;

import java.util.List;

public class ResponsModel {
    int total_count;
    boolean incomplate_results;
    List<Items> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplate_results() {
        return incomplate_results;
    }

    public void setIncomplate_results(boolean incomplate_results) {
        this.incomplate_results = incomplate_results;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
