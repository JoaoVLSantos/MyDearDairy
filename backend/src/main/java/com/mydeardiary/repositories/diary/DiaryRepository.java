package com.mydeardiary.repositories.diary;

import com.mydeardiary.model.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // Buscar todas as entradas de um usuário específico
    List<Diary> findByUserId(Long userId);

    // Buscar por data exata
    List<Diary> findByUserIdAndDate(Long userId, LocalDate date);

    // Buscar por palavra-chave no título ou na descrição (case-insensitive)
    @Query("SELECT d FROM Diary d WHERE d.user.id = :userId AND (LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Diary> searchByKeyword(Long userId, String keyword);
}