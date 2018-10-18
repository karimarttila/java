package simpleserver.domaindb.dto;

/**
 * Product entity.
 */
public class Product {

    // We provide fields as public since there is no reason why they should
    // be private in a simple DTO class like this.
    public int pgId;
    public int pId;
    public String title;
    public double price;
    // Let's have a minor shortcut for our OO model...
    public String author_or_director;
    public int year;
    public String country;
    public String genre_or_language;


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

}
