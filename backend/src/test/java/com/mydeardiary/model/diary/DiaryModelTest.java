package com.mydeardiary.model.diary;

import com.mydeardiary.model.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiaryModelTest {

    @Test
    void testDiaryConstructorAndGetters() {
        String title = "Meu dia";
        String description = "Foi um dia incrível";
        LocalDate date = LocalDate.of(2025, 4, 11);
        Mood mood = Mood.HAPPY;
        User user = new User();
        user.setId(1L);

        Diary diary = new Diary(title, description, date, mood, user);

        assertEquals(title, diary.getTitle());
        assertEquals(description, diary.getDescription());
        assertEquals(date, diary.getDate());
        assertEquals(mood, diary.getMood());
        assertEquals(user, diary.getUser());
    }

    @Test
    void testSetters() {
        Diary diary = new Diary();

        String title = "Título de Teste";
        String description = "Descrição de Teste";
        LocalDate date = LocalDate.of(2025, 1, 1);
        Mood mood = Mood.SAD;
        User user = new User();
        user.setId(2L);

        diary.setId(100L);
        diary.setTitle(title);
        diary.setDescription(description);
        diary.setDate(date);
        diary.setMood(mood);
        diary.setUser(user);

        assertEquals(100L, diary.getId());
        assertEquals(title, diary.getTitle());
        assertEquals(description, diary.getDescription());
        assertEquals(date, diary.getDate());
        assertEquals(mood, diary.getMood());
        assertEquals(user, diary.getUser());
    }
}

