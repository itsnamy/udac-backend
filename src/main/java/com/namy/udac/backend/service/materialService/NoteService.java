package com.namy.udac.backend.service.materialService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.learningMaterial.NoteMaterial;
import com.namy.udac.backend.repository.MaterialRepo.NoteMaterialRepository;

@Service
public class NoteService {
    
    @Autowired
    private NoteMaterialRepository noteRepo;

    public NoteMaterial addNote(NoteMaterial note) {
        // Generate new idLessonVideo
        int maxId = noteRepo.findMaxId();
        String newId = "note" + String.format("%03d", maxId + 1);
        note.setIdMaterialNote(newId);
        return noteRepo.add(note);
    }

    public NoteMaterial getById(String id) {
        return noteRepo.findById(id);
    }

    public List<NoteMaterial> getBySection(String idSection) {
        return noteRepo.findBySection(idSection);
    }

    public List<NoteMaterial> getAll() {
        return noteRepo.findAll();
    }

    public NoteMaterial updateNote(NoteMaterial updatedNote) {
        // Fetch the existing lesson video to update
        String id = updatedNote.getIdMaterialNote();
        NoteMaterial note = noteRepo.findById(id);
        if (note == null) {
            return null; // Note not found
        }
        // Update fields
        if (updatedNote.getIdSubtopicSection() != null) {
            note.setIdSubtopicSection(updatedNote.getIdSubtopicSection());
        }
        if (updatedNote.getNoteTitle() != null) {
            note.setNoteTitle(updatedNote.getNoteTitle());
        }
        if (updatedNote.getNoteContent() != null) {
            note.setNoteContent(updatedNote.getNoteContent());
        }
        return noteRepo.update(id, note);
    }

    public boolean deleteById(String id) {
        return noteRepo.deleteById(id) > 0;
    }
}
