package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);


        DataBaseService dataBaseService = context.getBean(DataBaseService.class);
        dataBaseService.removeAll();
        dataBaseService.createData(context, 150, 20, 500, 50);

//        Exercise_5 exercise_5 = new Exercise_5();
//        Map<String, Boolean> stringBooleanMap = exercise_5.doesCustomerGiveOpinionAboutProductThatHeReallyBought(
//                dataBaseService.getRandomOpinions(), dataBaseService.getRandomPurchases());
//        for (Map.Entry<String, Boolean> stringBooleanEntry : stringBooleanMap.entrySet()) {
//            System.out.println(stringBooleanEntry);
//        }
//
//        Exercise_6 exercise6 = context.getBean(Exercise_6.class);
//        exercise6.putInsertsFromFile();

//        Exercise_7 exercise7 = context.getBean(Exercise_7.class);
//        exercise7.removeOpinionsRatedUnder4(dataBaseService.getRandomOpinions());

//        Exercise_8 exercise8 = context.getBean(Exercise_8.class);
//        exercise8.removeCustomerWhoGiveLEssThan4Stars(dataBaseService.getRandomOpinions());

        Exercise_9 exercise9 = context.getBean(Exercise_9.class);
        exercise9.clientPurchase(1, 1, 1);

//        Exercise_10 exercise10 = context.getBean(Exercise_10.class);
//        exercise10.wipeDownExistenceOfProduct(1);
    }
}
