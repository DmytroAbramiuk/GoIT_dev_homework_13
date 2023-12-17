package com.example.homework.data.repository;

import com.example.homework.data.entity.Note;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NoteRepos {
    private final List<Note> noteList = new ArrayList<>();

    public List<Note> findAll () {
        return this.noteList;
    }

    public Optional<Note> findById (UUID id) {
        return this.noteList.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst();
    }

    public Note save(Note note) {
        if (Objects.isNull(note.getId())) {
            note.setId(UUID.randomUUID());
            this.noteList.add(note);
        } else {
            Optional<Note> optionalNote = this.noteList.stream()
                    .filter(n -> n.getId().equals(note.getId()))
                    .findFirst();
            if (optionalNote.isPresent()) {
                this.noteList.remove(optionalNote.get());
                this.noteList.add(note);
            }
        }
        return note;
    }

    public List<Note> saveAll(Collection<Note> notes) {
        notes.forEach(note -> note.setId(UUID.randomUUID()));
        this.noteList.addAll(notes);
        return notes.stream().toList();
    }

    public void deleteById(UUID id) {
        this.noteList.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .ifPresent(this.noteList::remove);
    }
}
