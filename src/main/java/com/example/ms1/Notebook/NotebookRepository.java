package com.example.ms1.Notebook;

import com.example.ms1.note.NoteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook,Long> {
}
