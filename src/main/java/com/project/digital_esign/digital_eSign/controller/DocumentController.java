package com.project.digital_esign.digital_eSign.controller;

import com.project.digital_esign.digital_eSign.dto.DocumentDTO;
import com.project.digital_esign.digital_eSign.dto.res.DocumentUploadResponse;
import com.project.digital_esign.digital_eSign.entity.Document;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.entity.UserPrincipal;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import com.project.digital_esign.digital_eSign.service.DocumentService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final UserInfoRepository userRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<DocumentDTO>> getAll(@AuthenticationPrincipal UserPrincipal currentUser){
        List<DocumentDTO> doc = documentService.getDocByUser(currentUser.getId());
        return ResponseEntity.ok(doc);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,@AuthenticationPrincipal UserPrincipal currentUser){
        documentService.deleteDocument(id,currentUser.getId());
        return ResponseEntity.ok("Document has been deleted");
    }

    @GetMapping("/getDetail/{id}")
    public ResponseEntity<DocumentDTO> getDocById(@PathVariable Long id,@AuthenticationPrincipal UserPrincipal currentUser){
        DocumentDTO dto = documentService.getDocDetails(id,currentUser.getId());
        if(dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);

    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file,
                                            @AuthenticationPrincipal UserPrincipal currentUser) {
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Please choose a file to upload");
        }

        try {
            UserInfo user = new UserInfo(currentUser.getId());

            Document saveDoc = documentService.saveDocument(file,user);
            return ResponseEntity.ok(new DocumentUploadResponse(
                saveDoc.getId(),
                    saveDoc.getTitle(),
                    saveDoc.getStatus(),
                    "Uploaded successfully "
            ));

        }catch (Exception e){
            return ResponseEntity.status(500).body("System Error: " + e.getMessage());
        }

    }

}
