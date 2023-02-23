package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED = "Conjured Mana Cake";

    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    private void applyQualityStep(Item item, int qualityStep) {
        int newQualityValue = item.quality + qualityStep;
        if (newQualityValue >= 0 && newQualityValue <= 50) {
            item.quality = newQualityValue;
        }
    }

    private int calculateBackstagePassIncrease(int sellIn) {
        int baseIncrease = 1;
        if (sellIn < 11) {
            baseIncrease++;
        }
        if (sellIn < 6) {
            baseIncrease++;
        }
        return baseIncrease;
    }

    private void updateQuality(Item item) {
        boolean isExpired = item.sellIn < 0 && !item.name.equals(GildedRose.SULFURAS);
        boolean reducesQuality = (!item.name.equals(GildedRose.AGED_BRIE) &&
            !item.name.equals(GildedRose.BACKSTAGE_PASS));

        int baseIncrease = item.name.equals(GildedRose.SULFURAS) ? 0 : 1;
        int changeRate = 1;

        if (reducesQuality)  {
            baseIncrease *= -1;
        }

        if (item.name.equals(GildedRose.BACKSTAGE_PASS)) {
            if(isExpired) {
                item.quality = 0;
                baseIncrease = 0;
            } else {
                baseIncrease = calculateBackstagePassIncrease(item.sellIn);
            }
        }

        if(isExpired) {
            changeRate *= 2;
        }

        if(item.name.equals(GildedRose.CONJURED)) {
            changeRate *= 2;
        }

        this.applyQualityStep(item, baseIncrease * changeRate);
    }

    private void updateSellInDate(Item item) {
        if (!item.name.equals(GildedRose.SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            updateSellInDate(item);
            updateQuality(item);
        });
    }
}
