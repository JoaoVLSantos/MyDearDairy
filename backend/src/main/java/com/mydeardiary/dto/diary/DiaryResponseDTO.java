package com.mydeardiary.dto.diary;

import com.mydeardiary.model.diary.Mood;

import java.time.LocalDate;

public class DiaryResponseDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private Mood mood;

    public DiaryResponseDTO(Long id, String title, String description, LocalDate date, Mood mood) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.mood = mood;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}