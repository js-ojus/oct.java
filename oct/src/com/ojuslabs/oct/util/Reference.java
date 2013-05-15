/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.util;

import static com.ojuslabs.oct.common.Constants.LIST_SIZE_S;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * An abstract base class for literature references.
 */
public abstract class Reference {
    // Unique reference ID in the references database.
    int                _id;
    String             _title;
    int                _year;
    int                _startPage;
    int                _endPage;
    final List<String> _authors;

    protected Reference() {
        _authors = Lists.newArrayListWithCapacity(LIST_SIZE_S);
    }

    /**
     * @return The unique ID of this literature reference.
     */
    public int id() {
        return _id;
    }

    /**
     * @param id
     *            The new unique ID to set for this literature reference.
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Reference ID must be a positive integer.");
        }

        _id = id;
    }

    /**
     * @return The title of this article.
     */
    public String title() {
        return _title;
    }

    /**
     * @param title
     *            The title to set for this article.
     * @throws IllegalArgumentException
     *             if the title is empty.
     */
    public void setTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Empty title given.");
        }

        _title = title;
    }

    /**
     * @return The year of publication.
     */
    public int year() {
        return _year;
    }

    /**
     * @param year
     *            The year of publication.
     */
    public void setyear(int year) {
        _year = year;
    }

    /**
     * @return The starting page of the article.
     */
    public int startPage() {
        return _startPage;
    }

    /**
     * @param startPage
     *            The starting page of the article.
     */
    public void setstartPage(int startPage) {
        if (startPage <= 0) {
            throw new IllegalArgumentException(
                    "Starting page number should be a positive integer.");
        }

        _startPage = startPage;
    }

    /**
     * @return The ending page of the article.
     */
    public int endPage() {
        return _endPage;
    }

    /**
     * @param endPage
     *            The ending page of the article.
     */
    public void setendPage(int endPage) {
        if (endPage <= 0) {
            throw new IllegalArgumentException(
                    "Ending page number should be a positive integer.");
        }

        _endPage = endPage;
    }

    /**
     * @return A read-only copy of the authors of this article.
     */
    public List<String> authors() {
        return ImmutableList.copyOf(_authors);
    }

    /**
     * @param author
     *            The author to add to the list of this article's authors.
     */
    public void addAuthor(String author) {
        if (_authors.contains(author)) {
            return;
        }

        _authors.add(author);
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object o);
}
