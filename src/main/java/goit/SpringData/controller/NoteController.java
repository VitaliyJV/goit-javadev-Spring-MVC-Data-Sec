package goit.SpringData.controller;

import goit.SpringData.service.INoteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import goit.SpringData.entity.Note;

@Controller
@RequestMapping("/note")
public class NoteController {

    private INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public ModelAndView getNotes() {
        var notes = noteService.listAll();
        ModelAndView result = new ModelAndView("list");
        result.addObject("notes", notes);
        return result;
    }

    @PostMapping("/delete")
    public ModelAndView deleteNote(@RequestParam(name = "id") Long id) {
        noteService.deleteById(id);
        return getNotes();
    }

    @GetMapping("/add")
    public ModelAndView addNote() {
        long i = 0;
        var note = new Note(i,"Insert your title","Insert your content");
        ModelAndView result = new ModelAndView("add");
        result.addObject("note", note);
        return result;
    }

    @GetMapping("/edit")
    public ModelAndView editNote(@RequestParam(name = "id") Long id) {
        var note = noteService.getById(id);
        ModelAndView result = new ModelAndView("edit");
        result.addObject("note", note);
        return result;
    }

    @PostMapping("/save")
    public ModelAndView save(Note note, final BindingResult bindingResult) {
        if(note.getId()==null)
            noteService.add(note);
        else
            noteService.update(note);
        return new ModelAndView("redirect:/note/list");
    }
}
