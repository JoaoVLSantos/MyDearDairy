package com.mydeardiary.dto.diary;

import com.mydeardiary.model.diary.Mood;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DiaryRequestDTO {

    public DiaryRequestDTO(String title, String description, LocalDate date, Mood mood){
        this.title = title;
        this.description = description;
        this.date = date;
        this.mood = mood;
        
    }

    @NotBlank(message = "A titulo não pode estar vazia")
    private String title;

    @NotBlank(message = "A descrição não pode estar vazia")
    private String description;

    @NotNull(message = "A data é obrigatória")
    private LocalDate date;

    @NotNull(message = "O humor é obrigatório")
    private Mood mood;

    // Getters e Setters

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