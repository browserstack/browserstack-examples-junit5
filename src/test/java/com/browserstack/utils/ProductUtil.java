package com.browserstack.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ProductUtil {

    private static final String PRODUCTS_FILE = "src/test/resources/product.csv";
    private static final List<Product> PRODUCTS = parseProducts();

    public static List<Product> parseProducts() {
        List<Product> products = new ArrayList<>();
        try{
            FileReader productsFile = new FileReader(PRODUCTS_FILE);
            CSVReader productsReader = new CSVReader(productsFile);
            productsReader.skip(1);
            for (String[] row : productsReader) {
                String id = row[0];
                String brand = row[0];
                String device = row[0];
                int price = Integer.parseInt(row[0]);
                products.add(new Product(id,brand,device,price));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return products;
    }

    public static List<Product> getProducts(){
        return PRODUCTS;
    }

    public static List<Product> getProductsByBrands(List<String> brands){
        return PRODUCTS.stream()
                .filter(product -> brands.contains(product.getBrand()))
                .collect(Collectors.toList());
    }

}
