package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

@Controller
@RequestMapping("/home/note")
public class NoteController {


    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public NoteController(NoteService noteService,  FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, Authentication authentication, Model model) {

        String noteStatus = "Note added successfully";

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
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));



        return "home";

    }


    @GetMapping("/delete/{noteid}")
    public String deleteNote(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, @PathVariable("noteid") Integer noteid, Authentication authentication, Model model) {

        String noteStatus = "Note deleted successfully";

        boolean isNoteDeleted = noteService.deleteNote(noteid, authentication.getName());

        if(!isNoteDeleted){

            noteStatus = "an error occurred while deleting the note";
            model.addAttribute("noteStatus", noteStatus);
        }
        model.addAttribute("noteStatus", noteStatus);

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));



        return "home";
    }


    @PostMapping("/edit")
    public String editNote(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, Authentication authentication, Model model) {

        String noteStatus = "Note edited successfully";

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
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));



        return "home";

    }
}
