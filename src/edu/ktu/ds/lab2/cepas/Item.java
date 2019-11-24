package edu.ktu.ds.lab2.cepas;

import edu.ktu.ds.lab2.demo.*;
import edu.ktu.ds.lab2.utils.Ks;
import edu.ktu.ds.lab2.utils.Parsable;

import java.time.LocalDate;
import java.util.*;

/**
 * @author EK
 */
public final class Item implements Parsable<Item> {

    // bendri duomenys visiems automobiliams (visai klasei)
    private static final int minYear = 1990;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 100.0;
    private static final double maxPrice = 333000.0;
    private static final String idCode = "TA";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja

    private final String carRegNr;

    private String make = "";
    private String model = "";
    private int year = -1;
    private int mileage = -1;
    private double price = -1.0;

    public Item() {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
    }

    public Item(String make, String model, int year, int mileage, double price) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        validate();
    }

    public Item(String dataString) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.parse(dataString);
        validate();
    }

    public Item(Builder builder) {
        carRegNr = idCode + (serNr++);    // suteikiamas originalus carRegNr
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.mileage = builder.mileage;
        this.price = builder.price;
        validate();
    }

    public Item create(String dataString) {
        return new Item(dataString);
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }
        
        if (!errorType.isEmpty()) {
            Ks.ern("Automobilis yra blogai sugeneruotas: " + errorType);
        }
    }

    @Override
    public void parse(String dataString) {
        try {   // duomenys, atskirti tarpais
            Scanner scanner = new Scanner(dataString);
            make = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setMileage(scanner.nextInt());
            setPrice(scanner.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų -> " + dataString);
        }
    }

    @Override
    public String toString() {  // papildyta su carRegNr
        return getCarRegNr() + "=" + make + "_" + model + ":" + year + " " + getMileage() + " " + String.format("%4.1f", price);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCarRegNr() {  //** nauja.
        return carRegNr;
    }

    @Override
    public int compareTo(Item car) {
        return getCarRegNr().compareTo(car.getCarRegNr());
    }

    public static Comparator<Item> byMake = (Item c1, Item c2) -> c1.make.compareTo(c2.make); // pradžioje pagal markes, o po to pagal modelius


    public static Comparator<Item> byYearPrice = (Item c1, Item c2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (c1.year > c2.year) {
            return +1;
        }
        if (c1.year < c2.year) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        if (c1.price < c2.price) {
            return -1;
        }
        return 0;
    };
    
    public final static Comparator<Object> byPrice = (Object obj1, Object obj2) ->
    {
        double prc1 = ((Item) obj1).getPrice();
        double prc2 = ((Item) obj2).getPrice();
        if(prc1<prc2) return -1;
        else if(prc1>prc2) return 1;
        return 0;
    };
    public final static Comparator<Object> byYearAndPrice2= (Object obj1, Object obj2) ->
    {
        Item book1 = (Item) obj1;
        Item book2 = (Item) obj2;
        if(book1.getYear() < book2.getYear()) return 1;
        if(book1.getYear() > book2.getYear()) return -1;
        if(book1.getPrice() < book2.getPrice()) return 1;
        if(book1.getPrice() > book2.getPrice()) return -1;
        return 0;
    };

    // Car klases objektų gamintojas (builder'is)
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] MODELS = { // galimų automobilių markių ir jų modelių masyvas
            {"Mazda", "121", "323", "626", "MX6"},
            {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
            {"Saab", "92", "96"},
            {"Honda", "Accord", "Civic", "Jazz"},
            {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
            {"Peugeot", "206", "207", "307"}
        };

        private String make = "";
        private String model = "";
        private int year = -1;
        private int mileage = -1;
        private double price = -1.0;

        public Item build() {
            return new Item(this);
        }

        public Item buildRandom() {
            int ma = RANDOM.nextInt(MODELS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(MODELS[ma].length - 1) + 1;// modelio indeksas 1..
            return new Item(MODELS[ma][0],
                    MODELS[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    6000 + RANDOM.nextInt(222000),// rida tarp 6000 ir 228000
                    800 + RANDOM.nextDouble() * 88000);// kaina tarp 800 ir 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder mileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
