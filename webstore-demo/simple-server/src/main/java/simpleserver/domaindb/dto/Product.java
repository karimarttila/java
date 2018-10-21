package simpleserver.domaindb.dto;

/**
 * Product entity.
 */
public class Product {

    // We provide fields as public since there is no reason why they should
    // be private in a simple DTO class like this.
    public final int pgId;
    public final int pId;
    public final String title;
    public final double price;
    // Let's have a minor shortcut for our OO model...
    public final String authorOrDirector;
    public final int year;
    public final String country;
    public final String genreOrLanguage;


    /**
     * Instantiates a new Product.
     *
     * @param pgId               the product group id
     * @param pId                the product id
     * @param title              the title
     * @param price              the price
     * @param authorOrDirector the author or director
     * @param year               the year
     * @param country            the country
     * @param genreOrLanguage  the genre or language
     */
    @SuppressWarnings({"squid:S00107"})
    public Product(int pgId, int pId, String title, double price,
                   String authorOrDirector, int year,
                   String country, String genreOrLanguage) {
        this.pgId = pgId;
        this.pId = pId;
        this.title = title;
        this.price = price;
        this.authorOrDirector = authorOrDirector;
        this.year = year;
        this.country = country;
        this.genreOrLanguage = genreOrLanguage;
    }

}
