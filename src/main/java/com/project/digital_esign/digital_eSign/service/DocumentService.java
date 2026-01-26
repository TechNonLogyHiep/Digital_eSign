package com.project.digital_esign.digital_eSign.service;

import com.project.digital_esign.digital_eSign.dto.DocumentDTO;
import com.project.digital_esign.digital_eSign.entity.Document;
import com.project.digital_esign.digital_eSign.entity.DocumentStatus;
import com.project.digital_esign.digital_eSign.entity.UserInfo;
import com.project.digital_esign.digital_eSign.repository.DocumentRepository;
import com.project.digital_esign.digital_eSign.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepo;
    private final String UPLOAD_DIR = "uploads/documents/";

    public List<DocumentDTO> getDocByUser(Long userId){
        List<Document> docs = documentRepo.findByUserId(userId);
        List<DocumentDTO> dtoList = new ArrayList<>();
        for(Document doc : docs){
            DocumentDTO dto = new DocumentDTO();
            dto.setId(doc.getId());
            dto.setTitle(doc.getTitle());
            dto.setStatus(doc.getStatus().toString());
            dto.setUploadedAt(doc.getUploadedAt());
            dto.setFileType(doc.getFileType());

            dtoList.add(dto);
        }

        return dtoList;
    }

    public DocumentDTO getDocDetails(Long docId,Long userId){
        Document doc = documentRepo.findById(docId).orElseThrow(() -> new RuntimeException("Document not found"));
        if(!doc.getUser().getId().equals(userId)){
            throw new UsernameNotFoundException("User can't see this document!");
        }
        DocumentDTO dtoDetail = new  DocumentDTO();
        dtoDetail.setId(doc.getId());
        dtoDetail.setTitle(doc.getTitle());
        dtoDetail.setStatus(doc.getStatus().toString());
        dtoDetail.setUploadedAt(doc.getUploadedAt());
        dtoDetail.setFileType(doc.getFileType());
        return dtoDetail;
    }

    @Transactional
    public void deleteDocument(Long docId,Long userId){
        Document doc = documentRepo.findById(docId).orElseThrow(() -> new RuntimeException("Document not found"));
        if(!doc.getUser().getId().equals(userId)){
            throw new UsernameNotFoundException("User do not have permission to delete this document!");
        }
        if(!"PENDING".equals(doc.getStatus().toString())){
            throw  new RuntimeException("This document was signed.Can not delete!");
        }
        try {
            Files.deleteIfExists(Paths.get(String.valueOf(Paths.get(doc.getFilePath()))));
        }catch (Exception e){
            System.err.println("Error to delete physical file: " + e.getMessage());
        }
        documentRepo.deleteById(docId);
    }

    public Document saveDocument(MultipartFile file, UserInfo user) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("Đã tạo thư mục lưu trữ tại: " + uploadPath.toAbsolutePath());
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path copyLocation = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
        Document document = new Document();
        document.setTitle(file.getOriginalFilename());
        document.setFilePath(copyLocation.toString());
        document.setFileType(file.getContentType());
        document.setStatus(DocumentStatus.PENDING);
        document.setUploadedAt(LocalDateTime.now());
        document.setUser(user);

        return documentRepo.save(document);
    }

}
