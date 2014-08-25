
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

public class DicksSet {
    private static DicksSet dicksSet;

    private ArrayList<Dicks> dicks = new ArrayList<Dicks>();

    private DicksSet() {
        dicks.add(new Dicks("Dick's in Wallingford",
                "This is the first Dick's which was opened in 1954.", R.drawable.wallingford,
                47.661147, -122.32774));
        dicks.add(new Dicks(
                "Dick's on Broadway",
                "This is the second location opened in 1955. Macklemore's video for \"White Walls\" was recently shot at this location.",
                R.drawable.broadway, 47.619321, -122.321111));
        dicks.add(new Dicks("Dick's on Holman", "This is the third location opened in 1960.",
                R.drawable.holman, 47.696417, -122.371693));
        dicks.add(new Dicks("Dick's in Lake City",
                "You guessed it. This is the 4th location opened in 1963.", R.drawable.lakecity,
                47.718214, -122.296956));
        dicks.add(new Dicks("Dick's on Queen Anne",
                "The fifth location, which was opened in 1974.", R.drawable.queenanne, 47.623454,
                -122.356479));
    }

    public static DicksSet getInstance() {
        if (dicksSet == null) {
            dicksSet = new DicksSet();
        }
        return dicksSet;
    }

    public ArrayList<Dicks> getLocations() {
        return dicks;
    }

    public Dicks getLocation(int id) {
        if (id >= dicks.size())
            return null;
        return dicks.get(id);
    }
}
