package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class JhipsterSettingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static JhipsterSetting getJhipsterSettingSample1() {
        return new JhipsterSetting().id(1L).theme("theme1").pageSize(1).others("others1");
    }

    public static JhipsterSetting getJhipsterSettingSample2() {
        return new JhipsterSetting().id(2L).theme("theme2").pageSize(2).others("others2");
    }

    public static JhipsterSetting getJhipsterSettingRandomSampleGenerator() {
        return new JhipsterSetting()
            .id(longCount.incrementAndGet())
            .theme(UUID.randomUUID().toString())
            .pageSize(intCount.incrementAndGet())
            .others(UUID.randomUUID().toString());
    }
}
