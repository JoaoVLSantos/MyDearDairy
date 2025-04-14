package com.mydeardiary.model.user;

import com.mydeardiary.model.diary.Diary;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void testConstructorAndGetters() {
        Long id = 1L;
        String name = "João";
        String email = "joao@example.com";
        String password = "senha123";
        LocalDate createdAt = LocalDate.of(2024, 1, 1);
        LocalDate updatedAt = LocalDate.of(2024, 2, 1);

        User user = new User(id, name, email, password, createdAt, updatedAt);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(Role.USER, user.getRole()); // padrão
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
        assertTrue(user.getDiaryEntries().isEmpty());
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setId(2L);
        user.setName("Maria");
        user.setEmail("maria@example.com");
        user.setPassword("senha456");
        user.setRole(Role.ADMIN);
        user.setCreatedAt(LocalDate.of(2023, 3, 1));
        user.setUpdatedAt(LocalDate.of(2023, 4, 1));

        assertEquals(2L, user.getId());
        assertEquals("Maria", user.getName());
        assertEquals("maria@example.com", user.getEmail());
        assertEquals("senha456", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals(LocalDate.of(2023, 3, 1), user.getCreatedAt());
        assertEquals(LocalDate.of(2023, 4, 1), user.getUpdatedAt());
    }

    @Test
    void testUserDetailsMethods() {
        User user = new User();
        user.setEmail("teste@exemplo.com");

        assertEquals("teste@exemplo.com", user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());

        assertEquals(1, user.getAuthorities().size());
        assertEquals("ROLE_USER", user.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testDiaryEntriesAssociation() {
        User user = new User();
        Diary entry = new Diary();
        user.setDiaryEntries(List.of(entry));

        assertEquals(1, user.getDiaryEntries().size());
        assertTrue(user.getDiaryEntries().contains(entry));
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("a@b.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("a@b.com");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setEmail("diferente@b.com");
        assertNotEquals(user1, user2);
    }

    @Test
    void testPrePersistAndPreUpdate() {
        User user = new User();
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());

        user.prePersist();
        assertEquals(LocalDate.now(), user.getCreatedAt());

        user.preUpdate();
        assertEquals(LocalDate.now(), user.getUpdatedAt());
    }
}

