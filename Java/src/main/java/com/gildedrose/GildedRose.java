package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED = "Conjured";

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

    private void updateQuality(Item item) {
        boolean isExpired = item.sellIn < 0;
        boolean reducesQuality = (!item.name.equals(GildedRose.AGED_BRIE) &&
            !item.name.equals(GildedRose.BACKSTAGE_PASS) &&
            !item.name.equals(GildedRose.SULFURAS));
        int baseModifier = 1;
        int qualityModifier = 1;

        if (reducesQuality)  {
            baseModifier = -1;
        }
        if (item.name.equals(GildedRose.AGED_BRIE) && isExpired) {
            qualityModifier = 2;
        }
        if (item.name.equals(GildedRose.BACKSTAGE_PASS)) {
            if (item.sellIn < 11) {
                baseModifier++;
            }

            if (item.sellIn < 6) {
                baseModifier++;
            }
        }
        this.applyQualityStep(item, baseModifier * qualityModifier);
    }

    private void updateSellInDate(Item item) {
        if (!item.name.equals(GildedRose.SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private void updateExpired(Item item) {
        if (!item.name.equals(GildedRose.AGED_BRIE)) {
            if (item.name.equals(GildedRose.BACKSTAGE_PASS)) {
                item.quality = 0;
            } else {
                this.applyQualityStep(item, -1);
            }
        }
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            updateSellInDate(item);
            updateQuality(item);
            if (item.sellIn < 0 && !item.name.equals(GildedRose.SULFURAS)) {
                updateExpired(item);
            }
        });
    }
}
