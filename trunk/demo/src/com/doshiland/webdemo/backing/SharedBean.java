package com.doshiland.webdemo.backing;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean that is attached to a "session" scope and not specific to a
 * "conversation".
 * 
 * @author <a href="mailto:jitesh@doshiland.com">Jitesh Doshi</a>
 */
public class SharedBean {
    private final static Log log = LogFactory.getLog(SharedBean.class);

    private String username;

    private String department;

    private Map<String,Long> employees;

    private Long[] approvedEmployees;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        log.debug("setting username:" + this.username + "->" + username);
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        log.debug("setting department:" + this.department + "->" + department);
        this.department = department;
    }

    public Map<String,Long> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<String,Long> employees) {
        this.employees = employees;
    }

    public Long[] getApprovedEmployees() {
        return approvedEmployees;
    }

    public void setApprovedEmployees(Long[] approvedEmployees) {
        this.approvedEmployees = approvedEmployees;
    }
}
