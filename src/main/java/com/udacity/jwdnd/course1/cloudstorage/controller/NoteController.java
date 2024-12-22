package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home/note")
public class NoteController {


    private final NoteService noteService;
    private final FileService fileService;

    public NoteController(NoteService noteService,  FileService fileService) {
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("saveNote") Note saveNote, Authentication authentication, Model model) {

        String noteStatus = null;

        if(saveNote != null) {

            boolean isNoteSaved = noteService.saveNote(saveNote, authentication.getName());

            if(!isNoteSaved){

                noteStatus = "An error occurred while adding the note";
            }
        }else{
            noteStatus = "No note added";
        }

        model.addAttribute("noteStatus", noteStatus);

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));


        return "home";

    }


    @GetMapping("/delete/{noteid}")
    public String deleteNote(@ModelAttribute("saveNote") Note saveNote, @PathVariable("noteid") Integer noteid, Authentication authentication, Model model) {

        boolean isNoteDeleted = noteService.deleteNote(noteid, authentication.getName());

        if(!isNoteDeleted){
            model.addAttribute("noteStatus", "an error occurred while deleting the note");
        }

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));


        return "home";
    }


    @PostMapping("/edit")
    public String editNote(@ModelAttribute("saveNote") Note saveNote, Authentication authentication, Model model) {

        String noteStatus = null;

        if(saveNote != null) {

            boolean isNoteSaved = noteService.editNote(saveNote);

            if(!isNoteSaved){

                noteStatus = "An error occurred while editing the note";
            }
        }else{
            noteStatus = "No note edited";
        }

        model.addAttribute("noteStatus", noteStatus);

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));


        return "home";

    }
}
