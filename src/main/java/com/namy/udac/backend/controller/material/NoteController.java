package com.namy.udac.backend.controller.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.learningMaterial.NoteMaterial;
import com.namy.udac.backend.service.materialService.NoteService;

@RestController
@RequestMapping("/material/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Add new note
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<NoteMaterial> addNote(@RequestBody NoteMaterial note) {
        NoteMaterial createdNote = noteService.addNote(note);  // service method named addVideo
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    // Get all notes
    @GetMapping("/view/all")
    public ResponseEntity<List<NoteMaterial>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAll());
    }

    // Get note by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<NoteMaterial> getNoteById(@PathVariable String id) {
        NoteMaterial note = noteService.getById(id);
        if (note == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(note);
    }

    // Get notes by subtopic section
    @GetMapping("/view/section/{idSection}")
    public ResponseEntity<List<NoteMaterial>> getNotesBySection(@PathVariable String idSection) {
        return ResponseEntity.ok(noteService.getBySection(idSection));
    }

    // Update existing note
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<NoteMaterial> updateNote(@PathVariable String id, @RequestBody NoteMaterial updatedNote) {
        updatedNote.setIdMaterialNote(id);
        NoteMaterial result = noteService.updateNote(updatedNote);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    // Delete note
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        boolean deleted = noteService.deleteById(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
