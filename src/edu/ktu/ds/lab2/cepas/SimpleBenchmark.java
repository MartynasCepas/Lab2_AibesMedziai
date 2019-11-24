/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.cepas;


import edu.ktu.ds.lab2.gui.ValidationException;
import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class SimpleBenchmark {

    public static final String FINISH_COMMAND = "                       ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab2.gui.messages");

    private static final String[] BENCHMARK_NAMES = {"addBstRec", "addBstIte", "addAvlRec", "removeBst", "treeAdd", "hashAdd", "treeContains", "hashContains"/*, "TreeTime", "ceiling", "floor","tailSet"*/};
    private static final int[] COUNTS = {10000, 20000, 40000, 80000, 200000};

    private final Timekeeper timeKeeper;
    private final String[] errors;

    private final SortedSet<Item> cSeries = new BstSet<>(Item.byPrice);
    private final SortedSet<Item> cSeries2 = new BstSetIterative<>(Item.byPrice);
    private final SortedSet<Item> cSeries3 = new AvlSet<>(Item.byPrice);

    private final TreeSet aSeries3 = new TreeSet<Integer>();
    private final HashSet aSeries2 = new HashSet<Integer>();
    private final TreeSet tSet = new TreeSet<Integer>();
    private final HashSet hSet = new HashSet<Integer>();
    private final SortedSet<Item> series = new BstSet<>(Item.byPrice);
    //private final Book bo;
    long f1=1, f2=1,f3=1;

    private final BstSet<Item> bSeries = new BstSet<>();

    /**
     * For console benchmark
     */
     public SimpleBenchmark() {
        timeKeeper = new Timekeeper(COUNTS);
        errors = new String[]{
                MESSAGES.getString("badSetSize"),
                MESSAGES.getString("badInitialData"),
                MESSAGES.getString("badSetSizes"),
                MESSAGES.getString("badShuffleCoef")
        };
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timeKeeper = new Timekeeper(COUNTS,0, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("badSetSize"),
            MESSAGES.getString("badInitialData"),
            MESSAGES.getString("badSetSizes"),
            MESSAGES.getString("badShuffleCoef")
        };
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
        
       //Ks.out( "Atmintis " + f1);
//        long memTotal = Runtime.getRuntime().totalMemory();
//        Ks.oun("memTotal= " + memTotal);
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    ParsableSortedSet<Item> GenerateTree(int k) {
        ParsableSortedSet<Item> pabandymas = new ParsableBstSet<>(Item::new);
        Item[] books = new ItemsGenerator().generateShuffle(k, 1.0);

        for (Item b : books) {
            pabandymas.add(b);
        }
        return pabandymas;
    }

   /* int CalculateTime(int k) {
        return ((BstSet<Book>) GenerateTree(k)).treeHeight(((BstSet<Book>) GenerateTree(k)).getRoot());
    }*/

    private void benchmark() throws InterruptedException {
        try {
            for (int k : COUNTS) {
                Item[] books = new ItemsGenerator().generateShuffle(k, 1.0);
                cSeries.clear();
                cSeries2.clear();
                cSeries3.clear();
                Arrays.stream(books).forEach(series::add);
                Item item =new Item(books[3].getModel(),books[3].getMake(),books[3].getMileage(),books[3].getYear(),books[3].getPrice());
                timeKeeper.startAfterPause();

                timeKeeper.start();
                Arrays.stream(books).forEach(cSeries::add);
                timeKeeper.finish(BENCHMARK_NAMES[0]);

                Arrays.stream(books).forEach(cSeries2::add);
                timeKeeper.finish(BENCHMARK_NAMES[1]);

                Arrays.stream(books).forEach(cSeries3::add);
                timeKeeper.finish(BENCHMARK_NAMES[2]);

                Arrays.stream(books).forEach(cSeries::remove);

                timeKeeper.finish(BENCHMARK_NAMES[3]);

                //------------------------------------------------
                for (Item b : books) {
                    aSeries3.add(b.getMileage());
                }
                timeKeeper.finish(BENCHMARK_NAMES[4]);

                for (Item b : books) {
                    aSeries2.add(b.getMileage());

                }
                timeKeeper.finish(BENCHMARK_NAMES[5]);

                for (Item b : books) {
                    aSeries3.containsAll(tSet);
                }
                timeKeeper.finish(BENCHMARK_NAMES[6]);

                for (Item b : books) {
                    aSeries2.containsAll(tSet);
                }
                timeKeeper.finish(BENCHMARK_NAMES[7]);
                
//                CalculateTime(k);
//                timeKeeper.finish(BENCHMARK_NAMES[8]);
//                
//                //long t1 = Runtime.getRuntime().totalMemory();
//                ((BstSet<Book>)series).ceiling(bok);
//                timeKeeper.finish(BENCHMARK_NAMES[9]);
//               // long t2 = Runtime.getRuntime().totalMemory();
//                ((BstSet<Book>)series).floor(bok);
//                timeKeeper.finish(BENCHMARK_NAMES[10]);
//               // long t3 = Runtime.getRuntime().totalMemory();
//                ((BstSet<Book>)series).tailset(bok);
//                timeKeeper.finish(BENCHMARK_NAMES[11]);
//               // long t4 = Runtime.getRuntime().totalMemory();
//               // long memTotal = Runtime.getRuntime().totalMemory();
        
                //--------------------------------------

                timeKeeper.seriesFinish();
//                f1=t2-t1;
//                f2=t3-t2;
//                f3=t4-t3;  
//                
               /// Ks.out( "Atmintis " + f1);
            }
            timeKeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                timeKeeper.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                timeKeeper.logResult(MESSAGES.getString("allSetIsPrinted"));
            } else {
                timeKeeper.logResult(e.getMessage());
            }
        }
    }
}
