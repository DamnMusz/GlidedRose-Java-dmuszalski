package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void updateQuality(Item item) {
        if (!item.name.equals(GildedRose.AGED_BRIE)
            && !item.name.equals(GildedRose.BACKSTAGE_PASS)) {
            if (item.quality > 0) {
                if (!item.name.equals(GildedRose.SULFURAS)) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals(GildedRose.BACKSTAGE_PASS)) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }
    }

    public void updateSellInDate(Item item) {
        if (!item.name.equals(GildedRose.SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    public void updateExpired(Item item) {
        if (item.sellIn < 0) {
            if (!item.name.equals(GildedRose.AGED_BRIE)) {
                if (!item.name.equals(GildedRose.BACKSTAGE_PASS)) {
                    if (item.quality > 0) {
                        if (!item.name.equals(GildedRose.SULFURAS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(item -> {
            updateQuality(item);
            updateSellInDate(item);
            updateExpired(item);
        });
    }
}
