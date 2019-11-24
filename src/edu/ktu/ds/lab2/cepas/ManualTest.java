/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.cepas;


import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

public class ManualTest {

    static Item[] cars;
    static ParsableSortedSet<Item> cSeries = new ParsableBstSet<>(Item::new, Item.byPrice);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() throws CloneNotSupportedException {
        Item c1 = new Item("RONALDO", "Laguna", 1997, 1700, 1000);
        Item c2 = new Item.Builder()
                .model("Lol")
                .make("Megane")
                .year(2001)
                .price(3500)
                .build();
        Item c3 = new Item.Builder().buildRandom();
        Item c4 = new Item("auto Laguna 2001 115900 700");
        Item c5 = new Item("super Megane 1946 365100 9500");
        Item c6 = new Item("barista   Civic  2001  36400 80.3");
        Item c7 = new Item("borderline Laguna 2001 115900 7500");
        Item c8 = new Item("domas Megane 1946 365100 950");
        Item c9 = new Item("jolandas   Civic  2007  36400 850.3");

        Item[] booksArray = {c9, c7, c8, c5, c1, c6};
        
        Ks.oun("Knygų Aibė:");
        ParsableSortedSet<Item> itemsSet = new ParsableBstSet<>(Item::new);
        AvlSet<Item> itemsSet1 = new ParsableAvlSet<>(Item::new);

        for (Item c : booksArray) {
            itemsSet.add(c);
            itemsSet1.add(c);
            Ks.oun("Aibė papildoma: " + c + ". Jos dydis: " + itemsSet.size());
        }
        Ks.oun("");
        Ks.oun(itemsSet.toVisualizedString(""));
        
        int[] keys = {8, 4, 10, 2, 6, 9, 12};
        Set<Integer> keyset = new BstSet();
        for (int c : keys) {
            keyset.add(c);
        }
        Ks.oun("Keyset");
        Ks.oun(keyset.toVisualizedString(""));
        
        int[] keys1 = {15, 3, 7};
        Set<Integer> keyset1 = new BstSet();
        for (int c : keys1) {
            keyset1.add(c);
        }   
        
        //**************************** Būtini
        Ks.oun("AddAll: " + ((BstSet<Integer>) keyset).addAll((BstSet<Integer>) keyset1));
            Ks.oun(keyset);
        
        Ks.oun(keyset.toVisualizedString(""));  
            
        Ks.oun("Higher: " + ((BstSet<Integer>) keyset).higher(1));
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Ks.oun("Floor: " + ((BstSet<Integer>) keyset).floor(13));
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Ks.oun("Height: " + ((BstSet<Integer>) keyset).findHeight());
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Ks.oun("PollLast: " + ((BstSet<Integer>) keyset).pollLast());
        Ks.oun(keyset);
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Ks.oun("Lower: " + ((BstSet<Integer>) keyset).lower(10));
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Ks.oun("PollFirst: " + ((BstSet<Integer>) keyset).pollFirst());
        Ks.oun(keyset);
        /*Ks.oun("");
        Ks.oun(keyset.toVisualizedString(""));  */  
        
        Set<Item> subset = itemsSet.subSet(c5, c7);
        Ks.oun("SUBSET " + c5 + " ir " + c7 + '\n' + subset.toString());
        Ks.oun("");
        Ks.oun(subset.toVisualizedString(""));   
        
        
        Ks.oun("TAILSET12 " + c5 + '\n' + itemsSet.tailSet(c5));
        Ks.oun("");
        Ks.oun(subset.toVisualizedString(""));  
        
        //**************************************
        
        //************************ 7 punktas
        Set<Item> headset = itemsSet.headSet(c7);
        Ks.oun("HEADSET " + c7 + '\n' + headset.toString());
        Ks.oun(headset.toVisualizedString(""));    
        
        Set<Item> subset2 = itemsSet.subSet(c5, c7);
        Ks.oun("SUBSET " + c5 + " ir " + c7 + '\n' + subset2.toString());
        Ks.oun(subset2.toVisualizedString(""));  
        
        Ks.oun("TAILSET1 " + c5 + '\n' + itemsSet.tailSet(c5));
        Ks.oun("");
        Ks.oun(subset.toVisualizedString(""));  
        
        ((Set<Item>) itemsSet).remove(c1);
        Ks.oun("REMOVE " + c1);
        Ks.oun(itemsSet);
        
        
     //   Ks.oun("Aukstis: " + ((BstSet<Integer>) keyset).treeHeight(((BstSet<Integer>) keyset).getRoot()));
     //   Ks.oun("");
     //   Ks.oun(keyset.toVisualizedString(""));   
        
    }

    static ParsableSortedSet generateSet(int kiekis, int generN) {
        cars = new Item[generN];
        for (int i = 0; i < generN; i++) {
            cars[i] = new Item.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(cars));

        cSeries.clear();
        Arrays.stream(cars).limit(kiekis).forEach(cSeries::add);
        return cSeries;
    }
}
