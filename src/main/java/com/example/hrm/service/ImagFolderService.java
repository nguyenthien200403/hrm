package com.example.hrm.service;

import com.example.hrm.config.GeneralResponse;
import com.example.hrm.dto.FolderDTO;
import com.example.hrm.model.Folder;
import com.example.hrm.model.Image;
import com.example.hrm.repository.EmployeeRepository;
import com.example.hrm.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagFolderService {
    private final FolderRepository repository;
    //private final EmployeeRepository employeeRepository;
    private final S3Service s3Service;

    @Value("${aws.url}")
    private String awsURL;

    public GeneralResponse<?> create(String id, List<MultipartFile> files) throws IOException {

        Folder folder = Folder.builder()
                .folderName("uploads/" + id + "/")
                .idEmployee(id)
                .build();

        folder.setImages(mapImage(files, folder));
        repository.save(folder);

        return new GeneralResponse<>(HttpStatus.OK.value(), "Successful upload images", null);
    }


    private List<Image> mapImage(List<MultipartFile> files, Folder folder) throws IOException {
       List<Image> list = new ArrayList<>();
       for(MultipartFile file :files){
           String key = folder.getFolderName() + file.getOriginalFilename();
           s3Service.uploadFileS3(file, key);
           Image image = Image.builder()
                   .fileName(file.getOriginalFilename())
                   .folder(folder)
                   .build();
           list.add(image);
       }
       return list;
    }

    public GeneralResponse<?> getImagesByEmployee(String id){
        Optional<Folder> findResult = repository.findByIdEmployee(id);
        if(findResult.isEmpty()){
            return new GeneralResponse<>(HttpStatus.NOT_FOUND.value(), "Not Found", null);
        }
        Folder folder = findResult.get();
        FolderDTO dto = new FolderDTO(folder, awsURL);
        return new GeneralResponse<>(HttpStatus.OK.value(), "Images", dto);
    }

}
