package com.zzikza.springboot.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void saveProduct() {
    }
}