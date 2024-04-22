package com.example.ms1.Notebook;

import com.example.ms1.note.Note;
import com.example.ms1.note.NoteRepository;
import com.example.ms1.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotebookController {

    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;
    private final NoteService noteService;

    @PostMapping("books/write")
    public String write() {
        Notebook notebook = new Notebook();
        notebook.setName("새로운 상위 노트북");

        Note note = noteService.saveDefault();
        notebook.addNote(note);

        notebookRepository.save(notebook);
        return "redirect:/";
    }

    @PostMapping("group/{notebookId}/books/write")
    public String groupWrite(@PathVariable("notebookId")Long notebookId) {

        Notebook parent = notebookRepository.findById(notebookId).orElseThrow();
        Notebook child = new Notebook();
        child.setName("새로운 하위 노트북");

        Note note = noteService.saveDefault();
        child.addNote(note);
        notebookRepository.save(child);

        parent.addChild(child);
        notebookRepository.save(parent);

        return "redirect:/";
    }

    @GetMapping("books/{id}")
    public String detail(@PathVariable Long id) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow();
        Note note = notebook.getNoteList().get(0);

        return "redirect:/books/%d/notes/%d".formatted(id,note.getId());
    }




}
