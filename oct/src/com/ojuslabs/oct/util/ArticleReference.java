/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.util;

/**
 * ArticleReference represents a published article.
 */
public final class ArticleReference extends Reference {
    private String _journal;
    private int    _volume;
    private int    _issue;

    public ArticleReference() {
        super();
    }

    /**
     * @return The journal in which this article was published.
     */
    public String journal() {
        return _journal;
    }

    /**
     * @param journal
     *            The journal in which this article was published.
     */
    public void setJournal(String journal) {
        if (journal.isEmpty()) {
            throw new IllegalArgumentException("Empty journal given.");
        }

        _journal = journal;
    }

    /**
     * @return The volume of the journal of publication.
     */
    public int volume() {
        return _volume;
    }

    /**
     * @param volume
     *            The volume of the journal of publication.
     */
    public void setVolume(int volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException(
                    "Volume number should be a positive integer.");
        }

        _volume = volume;
    }

    /**
     * @return The issue in the volume of publication.
     */
    public int issue() {
        return _issue;
    }

    /**
     * @param issue
     *            The issue in the volume of publication.
     */
    public void setIssue(int issue) {
        if (issue <= 0) {
            throw new IllegalArgumentException(
                    "Issue number should be a positive integer.");
        }

        _issue = issue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
                .format("ArticleReference [journal=%s, volume=%d, issue=%d, year=%d, startPage=%d, endPage=%d]",
                        _journal, _volume, _issue, _year, _startPage, _endPage);
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
        result = prime * result + _endPage;
        result = prime * result + _issue;
        result = prime * result
                + ((_journal == null) ? 0 : _journal.hashCode());
        result = prime * result + _startPage;
        result = prime * result + _volume;
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
        if (!(obj instanceof ArticleReference)) {
            return false;
        }
        ArticleReference other = (ArticleReference) obj;
        if (_endPage != other._endPage) {
            return false;
        }
        if (_issue != other._issue) {
            return false;
        }
        if (_journal == null) {
            if (other._journal != null) {
                return false;
            }
        }
        else if (!_journal.equals(other._journal)) {
            return false;
        }
        if (_startPage != other._startPage) {
            return false;
        }
        if (_volume != other._volume) {
            return false;
        }
        if (_year != other._year) {
            return false;
        }
        return true;
    }
}
