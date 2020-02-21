package com.groupdocs.ui.search.model;

public class SearchRequest {
    private String query;
    private String[] guids;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getGuids() {
        return guids;
    }

    public void setGuids(String[] guids) {
        this.guids = guids;
    }
}
