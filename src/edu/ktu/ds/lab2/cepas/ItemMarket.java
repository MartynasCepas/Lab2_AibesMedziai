package edu.ktu.ds.lab2.cepas;

import edu.ktu.ds.lab2.demo.*;
import edu.ktu.ds.lab2.utils.BstSet;
import edu.ktu.ds.lab2.utils.Set;

public class ItemMarket {

    public static Set<String> duplicateCarMakes(Item[] cars) {
        Set<Item> uni = new BstSet<>(Item.byMake);
        Set<String> duplicates = new BstSet<>();
        for (Item car : cars) {
            int sizeBefore = uni.size();
            uni.add(car);

            if (sizeBefore == uni.size()) {
                duplicates.add(car.getMake());
            }
        }
        return duplicates;
    }

    public static Set<String> uniqueCarModels(Item[] cars) {
        Set<String> uniqueModels = new BstSet<>();
        for (Item car : cars) {
            uniqueModels.add(car.getModel());
        }
        return uniqueModels;
    }
}
