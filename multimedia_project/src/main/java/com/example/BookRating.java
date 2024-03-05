package com.example;

import java.io.Serializable;

/**
 * Represents a book rating provided by a user.
 */
public class BookRating implements Serializable {

    /** The username of the user who provided the rating. */
    public String username;

    /** The rating provided by the user in integer format 1-5. */
    public Integer rating;

    /**
     * Constructs a new BookRating object with the given username and rating.
     *
     * @param username The username of the user who provided the rating.
     * @param rating The rating provided by the user.
     */
    public BookRating(String username, Integer rating) {
        this.username = username;
        this.rating = rating;
    }

    /**
     * Gets the rating provided by the user.
     *
     * @return The rating provided by the user.
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Gets the username of the user who provided the rating.
     *
     * @return The username of the user who provided the rating.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns a string representation of the BookRating object.
     *
     * @return A string representation of the BookRating object.
     */
    @Override
    public String toString() {
        return "Rating by " + username + '\n' + rating;
    }
}
