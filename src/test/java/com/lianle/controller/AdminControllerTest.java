package com.lianle.controller;

import com.lianle.entity.Film;
import com.lianle.service.FilmService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AdminControllerTest extends TestCase {

    @Autowired
    FilmService filmService;

    @Test
    public void testDoWork() throws Exception {
        System.out.println("start");
        Film film = new Film();
        filmService.save(film);
        System.out.println("end");
    }
}