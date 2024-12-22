package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/home/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model){
        File file = fileService.getFileByFileId(fileId, authentication.getName());

        ByteArrayResource downloadFile = new ByteArrayResource(file.getFileData());
        String fileName = "attachment; filename=\"" + file.getFileName() + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, fileName).contentLength(file.getFileData().length).body(downloadFile);

    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile uploadFile, Authentication authentication, Model model) throws IOException {

        String fileSaveStatus = null;

        if(!fileService.isFileExist(uploadFile.getOriginalFilename(), authentication.getName())){

            boolean isFileSaved = fileService.saveFile(uploadFile, authentication.getName());

            if(!isFileSaved){
                fileSaveStatus = "An error occurred while uploading the file";
            }

        }else{
            fileSaveStatus = "File already exists";
        }

        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("fileSaveStatus", fileSaveStatus);

        return "home";
    }


    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Authentication authentication, Model model){

        boolean isFileDleted = fileService.deleteFile(fileId, authentication.getName());

        if(!isFileDleted){
            model.addAttribute("fileSaveStatus", "an error occurred while deleting the file");
        }

        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        return "home";


    }





}
