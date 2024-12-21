package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/home")
public class HomeController {


    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping()
    public String home(Authentication authentication, Model model){


        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));


        return "home";
    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model){
        File file = fileService.getFileByFileId(fileId);

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentLength(file.getFileData().length)
                .body(resource);

    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadFile, Authentication authentication, Model model) throws IOException {

        boolean isFileSaved = fileService.saveFile(uploadFile, authentication.getName());


        if(!isFileSaved){
            model.addAttribute("isFileSaved", true);
        }

        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));

        return "home";



    }


}
