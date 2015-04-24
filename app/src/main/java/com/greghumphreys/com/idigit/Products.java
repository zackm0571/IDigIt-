package com.greghumphreys.com.idigit;

import java.util.Comparator;

/**
 * Created by zachmathews on 4/21/15.
 */
public class Products {

//Container for product information. Azure will serialize and deserialize so long as variable names are matching on the backend
    public String Id;

    public String category;

    public String productname;
    public String productdescription;

    public String userid;

    public int digs=0;
    public int needsworks=0;
    public int scraps=0;


}
