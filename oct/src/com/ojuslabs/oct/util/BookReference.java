/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.util;

/**
 * BookReference represents a published book or a part thereof.
 */
public class BookReference extends Reference {
    private String _publisher;
    private int    _edition;
    private String _isbn;

    public BookReference() {
        super();
        _edition = 1;
    }

    /**
     * @return The publisher of the book.
     */
    public String publisher() {
        return _publisher;
    }

    /**
     * @param publisher
     *            The publisher of the book.
     */
    public void setPublisher(String publisher) {
        if (publisher.isEmpty()) {
            throw new IllegalArgumentException("Empty publisher given.");
        }

        _publisher = publisher;
    }

    /**
     * @return The edition of the book.
     */
    public int edition() {
        return _edition;
    }

    /**
     * @param edition
     *            The edition of the book.
     */
    public void setEdition(int edition) {
        if (edition <= 0) {
            throw new IllegalArgumentException(
                    "Edition must be a positive integer.");
        }

        _edition = edition;
    }

    /**
     * @return The ISBN code of the book.
     */
    public String isbn() {
        return _isbn;
    }

    /**
     * @param isbn
     *            The ISBN code of the book.
     */
    public void setIsbn(String isbn) {
        if (isbn.isEmpty()) {
            throw new IllegalArgumentException("Empty ISBN code given.");
        }

        _isbn = isbn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
                .format("BookReference [publisher=%s, edition=%d, year=%d, isbn=%s]",
                        _publisher, _edition, _year, _isbn);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _edition;
        result = prime * result + ((_isbn == null) ? 0 : _isbn.hashCode());
        result = prime * result
                + ((_publisher == null) ? 0 : _publisher.hashCode());
        result = prime * result + _year;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BookReference)) {
            return false;
        }
        BookReference other = (BookReference) obj;
        if (_edition != other._edition) {
            return false;
        }
        if (_isbn == null) {
            if (other._isbn != null) {
                return false;
            }
        }
        else if (!_isbn.equals(other._isbn)) {
            return false;
        }
        if (_publisher == null) {
            if (other._publisher != null) {
                return false;
            }
        }
        else if (!_publisher.equals(other._publisher)) {
            return false;
        }
        if (_year != other._year) {
            return false;
        }
        return true;
    }
}
