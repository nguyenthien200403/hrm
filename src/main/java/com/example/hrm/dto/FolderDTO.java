package com.example.hrm.dto;

import com.example.hrm.model.Folder;
import lombok.Data;


import java.util.List;
import java.util.stream.Collectors;

@Data
public class FolderDTO {
    private String folderName;
    private List<ImageDTO> images;


    public FolderDTO(Folder folder, String awsURL){
        this.folderName = folder.getFolderName();
        this.images = folder.getImages().stream()
                .map(img -> new ImageDTO(awsURL + folderName + img.getFileName()))
                .collect(Collectors.toList());
    }
}
