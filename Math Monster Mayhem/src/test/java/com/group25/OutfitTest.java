package com.group25;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class OutfitTest {
    private Outfit outfit;

    @Before
    public void setUp() {
        outfit = new Outfit();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, outfit.getHat());
        assertEquals(0, outfit.getFace());
        assertEquals(0, outfit.getShirt());
    }

    @Test
    public void testSettersAndGetters() {

    }

    @Test
    public void testUpdateOutfit() {
        int[] outfitData = {9, 8, 7, 6, 5, 4, 3, 2};
        outfit.updateOutfit(outfitData);
        assertEquals(9, outfit.getHat());
        assertEquals(8, outfit.getFace());
        assertEquals(7, outfit.getShirt());
    }

}
