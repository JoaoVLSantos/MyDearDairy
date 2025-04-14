package com.mydeardiary.service.diary;

import com.mydeardiary.dto.diary.DiaryRequestDTO;
import com.mydeardiary.dto.diary.DiaryResponseDTO;
import com.mydeardiary.model.diary.Diary;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.diary.DiaryRepository;
import com.mydeardiary.repositories.user.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private UsersRepository userRepository;

    // Criação de uma nova entrada no diário
    public DiaryResponseDTO createDiaryEntry(DiaryRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));

        Diary diary = new Diary(
            dto.getTitle(),
            dto.getDescription(),
            dto.getDate(),
            dto.getMood(),
            user
        );

        Diary saved = diaryRepository.save(diary);
        return toResponseDTO(saved);
    }

    // Buscar todas as entradas de um usuário
    public List<DiaryResponseDTO> getAllEntries(Long userId) {
        return diaryRepository.findByUserId(userId)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    // Buscar por data
    public List<DiaryResponseDTO> getEntriesByDate(Long userId, LocalDate date) {
        return diaryRepository.findByUserIdAndDate(userId, date)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    // Buscar por palavra-chave
    public List<DiaryResponseDTO> searchByKeyword(Long userId, String keyword) {
        return diaryRepository.searchByKeyword(userId, keyword)
            .stream()
            .map(this::toResponseDTO)
            .toList();
    }

    // Conversor de entidade para DTO de resposta
    private DiaryResponseDTO toResponseDTO(Diary diary) {
        return new DiaryResponseDTO(
            diary.getId(),
            diary.getTitle(),
            diary.getDescription(),
            diary.getDate(),
            diary.getMood()
        );
    }
}