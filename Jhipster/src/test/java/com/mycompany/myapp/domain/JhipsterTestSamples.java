package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JhipsterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Jhipster getJhipsterSample1() {
        return new Jhipster().id(1L).name("name1").setting("setting1");
    }

    public static Jhipster getJhipsterSample2() {
        return new Jhipster().id(2L).name("name2").setting("setting2");
    }

    public static Jhipster getJhipsterRandomSampleGenerator() {
        return new Jhipster().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).setting(UUID.randomUUID().toString());
    }
}
