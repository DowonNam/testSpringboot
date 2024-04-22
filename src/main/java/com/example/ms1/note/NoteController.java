package com.example.ms1.note;

import com.example.ms1.Notebook.Notebook;
import com.example.ms1.Notebook.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{notebookId}/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private final NotebookRepository notebookRepository;


    @PostMapping("/write")
    public String write(@PathVariable("notebookId")Long notebookId) {
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow();
        Note note = noteService.saveDefault();
        notebook.addNote(note);
        notebookRepository.save(notebook);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(Model model,
                         @PathVariable("notebookId") Long notebookId,
                         @PathVariable Long id) {
        Note note = noteRepository.findById(id).get();

        List<Notebook> notebookList = notebookRepository.findAll();
        Notebook taegetNotebook = notebookRepository.findById(notebookId).get();
        List<Note> noteList = noteRepository.findByNotebook(taegetNotebook);

        model.addAttribute("notebookList",notebookList);
        model.addAttribute("noteList",noteList);
        model.addAttribute("targetNote", note);
        model.addAttribute("noteList", noteRepository.findAll());

        return "main";
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable("notebookId") Long notebookId,
                         @PathVariable("id") Long id, String title, String content) {
        Note note = noteRepository.findById(id).get();

        if(title.trim().length()==0){
            title="제목 없음";
        }

        note.setTitle(title);
        note.setContent(content);

        noteRepository.save(note);
        return "redirect:/books/%d/notes/%d".formatted(notebookId,id);
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("notebookId") Long notebookId,
                         @PathVariable("id") Long id) {

        noteRepository.deleteById(id);
        return "redirect:/";
    }

}
