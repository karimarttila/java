package simpleserver.domaindb.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Product entity.
 */
public class Product {

    private int pgId;
    private int pId;
    private String title;
    private double price;
    // Let's have a minor shortcut for our OO model...
    private String author_or_director;
    private int year;
    private String country;
    private String genre_or_language;


    /**
     * Instantiates a new Product.
     *
     * @param pgId               the product group id
     * @param pId                the product id
     * @param title              the title
     * @param price              the price
     * @param author_or_director the author or director
     * @param year               the year
     * @param country            the country
     * @param genre_or_language  the genre or language
     */
    public Product(int pgId, int pId, String title, double price,
                   String author_or_director, int year,
                   String country, String genre_or_language) {
        this.pgId = pgId;
        this.pId = pId;
        this.title = title;
        this.price = price;
        this.author_or_director = author_or_director;
        this.year = year;
        this.country = country;
        this.genre_or_language = genre_or_language;
    }

    /**
     * Gets product grou id.
     *
     * @return the product group id
     */
    public int getPgId() {
        return pgId;
    }

    /**
     * Sets product group id.
     *
     * @param pgId the product group id
     */
    public void setPgId(int pgId) {
        this.pgId = pgId;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getpId() {
        return pId;
    }

    /**
     * Sets product id.
     *
     * @param pId the product id
     */
    public void setpId(int pId) {
        this.pId = pId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets author or director.
     *
     * @return the author or director
     */
    public String getAuthor_or_director() {
        return author_or_director;
    }

    /**
     * Sets author or director.
     *
     * @param author_or_director the author or director
     */
    public void setAuthor_or_director(String author_or_director) {
        this.author_or_director = author_or_director;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets genre or language.
     *
     * @return the genre or language
     */
    public String getGenre_or_language() {
        return genre_or_language;
    }

    /**
     * Sets genre or language.
     *
     * @param genre_or_language the genre or language
     */
    public void setGenre_or_language(String genre_or_language) {
        this.genre_or_language = genre_or_language;
    }
}
