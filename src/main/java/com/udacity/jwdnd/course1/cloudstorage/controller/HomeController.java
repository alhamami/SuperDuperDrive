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

@Controller
@RequestMapping("/home")
public class HomeController {


    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String home(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, Authentication authentication, Model model){

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));

        return "home";
    }

}
