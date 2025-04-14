package com.mydeardiary.service.diary;

import com.mydeardiary.dto.diary.DiaryRequestDTO;
import com.mydeardiary.dto.diary.DiaryResponseDTO;
import com.mydeardiary.model.diary.Diary;
import com.mydeardiary.model.diary.Mood;
import com.mydeardiary.model.user.User;
import com.mydeardiary.repositories.diary.DiaryRepository;
import com.mydeardiary.repositories.user.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiaryServiceTest {

    private DiaryRepository diaryRepository;
    private UsersRepository usersRepository;
    private DiaryService diaryService;

    @BeforeEach
    void setUp() {
        diaryRepository = mock(DiaryRepository.class);
        usersRepository = mock(UsersRepository.class);
        diaryService = new DiaryService();
        diaryService.diaryRepository = diaryRepository;
        diaryService.userRepository = usersRepository;
    }

    @Test
    void testCreateDiaryEntrySuccessfully() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        DiaryRequestDTO dto = new DiaryRequestDTO(
            "Meu dia",
            "Hoje foi um ótimo dia",
            LocalDate.of(2025, 4, 13),
            Mood.HAPPY
        );

        Diary savedDiary = new Diary(dto.getTitle(), dto.getDescription(), dto.getDate(), dto.getMood(), user);
        savedDiary.setId(100L);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(diaryRepository.save(any(Diary.class))).thenReturn(savedDiary);

        DiaryResponseDTO result = diaryService.createDiaryEntry(dto, userId);

        assertEquals("Meu dia", result.getTitle());
        assertEquals("Hoje foi um ótimo dia", result.getDescription());
        assertEquals(Mood.HAPPY, result.getMood());
        assertEquals(100L, result.getId());
    }

    @Test
    void testCreateDiaryEntryUserNotFound() {
        Long userId = 2L;
        DiaryRequestDTO dto = new DiaryRequestDTO("Sem usuário", "Sem dados", LocalDate.now(), Mood.SAD);

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            diaryService.createDiaryEntry(dto, userId);
        });
    }

    @Test
    void testGetAllEntriesReturnsList() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Diary d1 = new Diary("Título 1", "Desc 1", LocalDate.now(), Mood.VERY_HAPPY, user);
        d1.setId(1L);
        Diary d2 = new Diary("Título 2", "Desc 2", LocalDate.now(), Mood.HAPPY, user);
        d2.setId(2L);

        when(diaryRepository.findByUserId(userId)).thenReturn(List.of(d1, d2));

        List<DiaryResponseDTO> results = diaryService.getAllEntries(userId);

        assertEquals(2, results.size());
        assertEquals("Título 1", results.get(0).getTitle());
        assertEquals("Título 2", results.get(1).getTitle());
    }

    @Test
    void testGetEntriesByDateReturnsList() {
        Long userId = 1L;
        LocalDate date = LocalDate.of(2025, 4, 13);
        User user = new User();
        user.setId(userId);

        Diary diary = new Diary("Dia específico", "descrição", date, Mood.SAD, user);
        diary.setId(10L);

        when(diaryRepository.findByUserIdAndDate(userId, date)).thenReturn(List.of(diary));

        List<DiaryResponseDTO> result = diaryService.getEntriesByDate(userId, date);

        assertEquals(1, result.size());
        assertEquals("Dia específico", result.get(0).getTitle());
    }

    @Test
    void testSearchByKeywordReturnsList() {
        Long userId = 1L;
        String keyword = "ótimo";
        User user = new User();
        user.setId(userId);

        Diary diary = new Diary("Título", "Hoje foi ótimo", LocalDate.now(), Mood.HAPPY, user);
        diary.setId(99L);

        when(diaryRepository.searchByKeyword(userId, keyword)).thenReturn(List.of(diary));

        List<DiaryResponseDTO> result = diaryService.searchByKeyword(userId, keyword);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getDescription().contains("ótimo"));
    }
}