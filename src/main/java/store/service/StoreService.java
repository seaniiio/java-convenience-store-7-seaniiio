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
        List<String> promotionsRaw = Reader.readPromotions();
        List<Promotion> promotions = new ArrayList<>();
        for (String promotionRaw : promotionsRaw) {
            promotions.add(Promotion.createPromotion(Parser.splitByComma(promotionRaw)));
        }
        storeRepository.savePromotions(promotions);
    }

    private void initProducts() {
        List<String> productsRaw = Reader.readProducts();
        for (String productRaw : productsRaw) {
            List<String> parsedProduct = Parser.splitByComma(productRaw);
            Promotion promotion = storeRepository.findPromotionByName(parsedProduct.get(PROMOTION_INDEX));
            // 다른 재고가 존재하면 재고만 추가
            Product existProduct = storeRepository.findProductByName(parsedProduct.get(0));
            if (existProduct != null) {
                existProduct.addStockToProduct(parsedProduct, promotion);
                continue;
            }
            // 존재하지 않는 상품이면 새로 만들기
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
