package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Exercise_10 {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Transactional
    public void wipeDownExistenceOfProduct(int productID) {
        String deleteOpinionOfProduct = "DELETE FROM OPINION WHERE PRODUCT_ID = ?";
        String deletePurchaseOfProduct = "DELETE FROM PURCHASE WHERE PRODUCT_ID = ?";
        String deleteProduct = "DELETE FROM PRODUCT WHERE ID = ?";

        JdbcTemplate template = new JdbcTemplate(simpleDriverDataSource);
        template.update(deleteOpinionOfProduct, productID);
        template.update(deletePurchaseOfProduct, productID);
        template.update(deleteProduct, productID);
    }
}
