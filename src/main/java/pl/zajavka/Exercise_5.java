package pl.zajavka;

import org.springframework.stereotype.Service;
import pl.zajavka.data.Opinion;
import pl.zajavka.data.Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Exercise_5 {

    public Map<String, Boolean> doesCustomerGiveOpinionAboutProductThatHeReallyBought
            (List<Opinion> opinionList, List<Purchase> purchaseList)
    {
        Map<Integer, List<Integer>> customersWhoAddAnOpinion = opinionList.stream()
                .sorted()
                .collect(Collectors.toMap(
                        Opinion::getCustomerId,
                        o -> List.of(o.getProductId()),
                        (o1, o2) -> {
                            List<Integer> list = new ArrayList<>(o1);
                            list.addAll(o2);
                            return list;
                        }));
        Map<Integer, List<Integer>> customersWhoPurchase = purchaseList.stream()
                .sorted()
                .collect(Collectors.toMap(
                        Purchase::getCustomerId,
                        o -> List.of(o.getProductId()),
                        (o1, o2) -> {
                            List<Integer> list = new ArrayList<>(o1);
                            list.addAll(o2);
                            return list;
                        }));

        System.out.println(customersWhoAddAnOpinion);
        System.out.println(customersWhoPurchase);
        Map<String, Boolean> clientsIdAndProductThatHeRated_andCheckIfHisOpinionWasLegitimate = new HashMap<>();

        for (Map.Entry<Integer, List<Integer>> integerListEntry : customersWhoAddAnOpinion.entrySet()) {
            List<Integer> listOfPurchaseProductsIds;
            try {
                listOfPurchaseProductsIds = customersWhoPurchase.get(integerListEntry.getKey());
                List<Integer> value = integerListEntry.getValue();
                value = value.stream().distinct().toList();

                for (Integer integer : value) {
                    if (listOfPurchaseProductsIds.contains(integer)) {
                        clientsIdAndProductThatHeRated_andCheckIfHisOpinionWasLegitimate
                                .put(String.format("client id: %s product id: %s", integerListEntry.getKey(), integer), true);
                    } else {
                        clientsIdAndProductThatHeRated_andCheckIfHisOpinionWasLegitimate
                                .put(String.format("client id: %s product id: %s", integerListEntry.getKey(), integer), false);
                    }
                }
            } catch (Exception exception) {
                clientsIdAndProductThatHeRated_andCheckIfHisOpinionWasLegitimate
//                        .put(String.format("client id: %s product id: %s", integerListEntry.getKey(), integerListEntry.getValue()), false);
                        .put(String.format("Client with id: %s doesn't bought anything from the store and yet he added an opinion", integerListEntry.getKey()), false);
            }
        }
        return clientsIdAndProductThatHeRated_andCheckIfHisOpinionWasLegitimate;
    }
}
