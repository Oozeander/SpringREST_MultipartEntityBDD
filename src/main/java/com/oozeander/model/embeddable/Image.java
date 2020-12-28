/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.model.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author b.ketrouci
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@JsonIgnoreProperties({"fileData"})
@JsonPropertyOrder({"fileName", "fileExtension", "fileSize"})
public class Image {

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_extension")
    private String fileExtension;
    @Column(name = "file_size")
    private String fileSize;
    @Lob
    @Column(name = "file_data")
    private Blob fileData;
}
