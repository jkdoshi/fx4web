package com.doshiland.webdemo.backing;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean that is attached to a "conversation" scope and not shared across the
 * "session".
 * 
 * @author <a href="mailto:jitesh@doshiland.com">Jitesh Doshi</a>
 */
public class UnsharedBean {
    private String text;

    private List<String> list = new ArrayList<String>();

    /**
     * Text entered by the user and being inserted into the list.
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * The list that maintains all text entries made by the user.
     */
    public List getList() {
        return list;
    }

    /**
     * Adds the currently entered text as an element to the list.
     */
    public void addRow() {
        list.add(text);
    }
}
