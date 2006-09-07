package com.doshiland.webdemo.backing;

import java.util.Map;

public class Employees {
    private Map<String, Long> all;

    private Long[] approved;

    public Map<String, Long> getAll() {
        return all;
    }

    public void setAll(Map<String, Long> all) {
        this.all = all;
    }

    public Long[] getApproved() {
        return approved;
    }

    public void setApproved(Long[] approved) {
        this.approved = approved;
    }

}
