package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }


    public boolean saveNote(Note note, String username){

        User user = userService.getUserByUsername(username);
        note.setUserid(user.getUserid());
        int savedNote = noteMapper.saveNote(note);

        return savedNote > 0;

    }

    public List<Note> getAllNotes(String username){
        return noteMapper.getAllNotes(username);
    }


    public boolean deleteNote(Integer noteid, String username){

        int deletedNote = noteMapper.deleteNote(noteid, username);

        return deletedNote > 0;
    }

    public boolean editNote(Note note){

        int editedNote = noteMapper.editNote(note);

        return editedNote > 0;
    }
}
