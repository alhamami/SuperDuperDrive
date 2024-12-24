package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public CredentialController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, Authentication authentication, Model model) {

        String credentialStatus = null;

        if(saveCredential != null) {

            boolean isCredentialSaved = credentialService.saveCredential(saveCredential, authentication.getName());

            if(!isCredentialSaved){

                credentialStatus = "An error occurred while adding the note";
            }
        }else{
            credentialStatus = "No note added";
        }

        model.addAttribute("credentialStatus", credentialStatus);

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));

        return "home";

    }

    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@ModelAttribute("saveNote") Note saveNote,  @ModelAttribute("saveCredential") Credential saveCredential, @PathVariable("credentialid") Integer credentialid, Authentication authentication, Model model) {

        boolean isCredentialDeleted = credentialService.deleteCredential(credentialid, authentication.getName());

        if(!isCredentialDeleted){
            model.addAttribute("noteStatus", "an error occurred while deleting the note");
        }

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));



        return "home";
    }


    @PostMapping("/edit")
    public String editCredential(@ModelAttribute("saveNote") Note saveNote, @ModelAttribute("saveCredential") Credential saveCredential, Authentication authentication, Model model) {

        String credentialStatus = null;

        if(saveNote != null) {

            boolean isCredentialSaved = credentialService.editCredential(saveCredential);

            if(!isCredentialSaved){

                credentialStatus = "An error occurred while editing the note";
            }
        }else{
            credentialStatus = "No note edited";
        }

        model.addAttribute("credentialStatus", credentialStatus);

        model.addAttribute("notes", noteService.getAllNotes(authentication.getName()));
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getName()));
        model.addAttribute("decryptedCredentials", credentialService.getAllDecryptedCredentials(authentication.getName()));



        return "home";

    }


}
