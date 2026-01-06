package com.example.hrm.docuseal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocuSeal {
    private String id;

    private String status;

    @JsonProperty("embed_src")
    private String embedSrc;


    @JsonProperty("completed_at")
    private String completedAt;
}
