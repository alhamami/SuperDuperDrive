package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }


    public boolean isFileExist(String fileName) {
        return fileMapper.getFileByFileName(fileName) != null;
    }

    public List<File> getAllFiles(String username){
        return fileMapper.getAllFiles(username);
    }

    public File getFileByFileId(Integer fileId){
        return fileMapper.getFileByFileId(fileId);
    }

    public boolean saveFile(MultipartFile file, String username) throws IOException {


        User user = userService.getUserByUsername(username);
        File savefile =  new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), user.getUserid(), file.getBytes());

        int savedFile = fileMapper.savetFile(savefile);

        return savedFile > 0;

    }




}
