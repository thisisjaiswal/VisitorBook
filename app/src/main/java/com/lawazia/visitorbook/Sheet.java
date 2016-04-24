package com.lawazia.visitorbook;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Sheet implements Serializable
{
    private static final long serialVersionUID = 1L;
        int bookId;
        int id;
        Date created;
        String name;

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(name);
    }

    public String getCreatedString() {
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(created);
    }
}
