package com.jmdm.squiz.service;

import com.jmdm.squiz.domain.Pdf;
import com.jmdm.squiz.repository.PdfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfUploadService {
    private final PdfRepository pdfRepository;

    @Value("${file.dir}")
    private String fileDir;

    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String extractExt(String uploadFileName) {
        int pos = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(pos+1);
    }
    private String createStoredFileName(String uploadFileName) {
        String ext = extractExt(uploadFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public void uploadPdf(String memberId, MultipartFile pdf) throws IOException {
        if (pdf.isEmpty()) {
            return;
        }
        String uploadFileName = pdf.getOriginalFilename();
        String storedFileName = createStoredFileName(uploadFileName);
        String storedPath = getFullPath(storedFileName);
        pdf.transferTo(new File(storedPath));
        Pdf storedPdf = Pdf.setPdf(memberId, uploadFileName, storedFileName,storedPath);
        pdfRepository.save(storedPdf);
        // 업로드 파일명, 서버 저장 명 선언 및 초기화
        //application.yml에 file 저장 dir 선언 후 그 경로로
    }

}