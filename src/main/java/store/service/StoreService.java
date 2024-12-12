package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.ProductDto;
import store.repository.StoreRepository;
import store.util.Parser;
import store.util.Reader;

public class StoreService {

    private static final int PROMOTION_INDEX = 3;
    private final StoreRepository storeRepository = StoreRepository.getInstance();

    public void initStore() {
        initPromotions();
        initProducts();
    }

    private void initPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        for (String promotionRaw : Reader.readPromotions()) {
            promotions.add(Promotion.createPromotion(Parser.splitByComma(promotionRaw)));
        }
        storeRepository.savePromotions(promotions);
    }

    private void initProducts() {
        for (String productRaw : Reader.readProducts()) {
            List<String> parsedProduct = Parser.splitByComma(productRaw);
            Promotion promotion = storeRepository.findPromotionByName(parsedProduct.get(PROMOTION_INDEX));
            Product existProduct = storeRepository.findProductByName(parsedProduct.get(0));
            if (existProduct != null) {
                existProduct.addStockToProduct(parsedProduct, promotion);
                continue;
            }
            storeRepository.saveProduct(Product.createProduct(parsedProduct, promotion));
        }
    }

    public List<ProductDto> getProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = storeRepository.getProducts();
        for (Product product : products) {
            if (product.isPromotionStockExist()) {
                productDtos.add(new ProductDto(product.getName(), product.getPrice(), product.getPromotionStock(), product.getPromotion().getName()));
            }
            //일반재고는 무조건 있음
            productDtos.add(new ProductDto(product.getName(), product.getPrice(), product.getNormalStock(), ""));
        }
        return productDtos;
    }
}
