package com.mydeardiary.controller.diary;

import com.mydeardiary.dto.diary.DiaryRequestDTO;
import com.mydeardiary.dto.diary.DiaryResponseDTO;
import com.mydeardiary.service.diary.DiaryService;
import com.mydeardiary.model.user.User;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponseDTO> createEntry(
            @RequestBody @Valid DiaryRequestDTO dto,
            @AuthenticationPrincipal User user) {

        DiaryResponseDTO response = diaryService.createDiaryEntry(dto, user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DiaryResponseDTO>> getAllEntries(@AuthenticationPrincipal User user) {
        List<DiaryResponseDTO> entries = diaryService.getAllEntries(user.getId());
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/date")
    public ResponseEntity<List<DiaryResponseDTO>> getEntriesByDate(
            @AuthenticationPrincipal User user,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<DiaryResponseDTO> entries = diaryService.getEntriesByDate(user.getId(), date);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiaryResponseDTO>> searchByKeyword(
            @AuthenticationPrincipal User user,
            @RequestParam("keyword") String keyword) {

        List<DiaryResponseDTO> entries = diaryService.searchByKeyword(user.getId(), keyword);
        return ResponseEntity.ok(entries);
    }
}