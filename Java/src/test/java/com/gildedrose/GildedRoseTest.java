package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {
    @ParameterizedTest
    @MethodSource("qualityTestCases")
    void itemsNameEqualsFoo(QualityTestCase[] testCases) {
        for (QualityTestCase testCase : testCases) {
            GildedRose app = new GildedRose(testCase.items);
            app.updateQuality();
            assertEquals(testCase.expectedSellIn, app.getItems()[0].sellIn);
            assertEquals(testCase.expectedQuality, app.getItems()[0].quality);
        }
    }

    static class QualityTestCase {
        private String testName;
        private Item[] items;
        private int expectedQuality;

        private int expectedSellIn;

        QualityTestCase(String testName, Item item, int expectedSellIn, int expectedQuality) {
            this.testName = testName;
            this.items = new Item[] { item };
            this.expectedSellIn = expectedSellIn;
            this.expectedQuality = expectedQuality;
        }
    }

    static Stream<Arguments> qualityTestCases() {
        return Stream.of(
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("simpleDecrease", new Item("foo", 1, 2), 0,1)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("expiredDoubleDecrease", new Item("foo", 0, 1), -1,0)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("neverNegative", new Item("foo", -2, 0), -3,0)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("agedBrieIncrease", new Item(GildedRose.AGED_BRIE, 2, 1), 1,2)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("expiredAgedBrieDoubleIncrease", new Item(GildedRose.AGED_BRIE, -1, 1), -2,3)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("maxQualityLimit", new Item(GildedRose.AGED_BRIE, 1, 50), 0,50)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("sulfurasDontChangeQuality", new Item(GildedRose.SULFURAS, 4, 80), 4,80)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("passesMoreThan10DaysIncrease", new Item(GildedRose.BACKSTAGE_PASS, 12, 1), 11,2)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("passesLessThan10DaysIncrease", new Item(GildedRose.BACKSTAGE_PASS, 10, 1), 9,3)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("passesLessThan5DaysIncrease", new Item(GildedRose.BACKSTAGE_PASS, 5, 1), 4,4)
            }),
            Arguments.of((Object) new QualityTestCase[] {
                new QualityTestCase("passesAfterConcert", new Item(GildedRose.BACKSTAGE_PASS, 0, 8), -1,0)
            })
        );
    }
}
