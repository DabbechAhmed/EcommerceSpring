package com.pleaseWorkNow.hope.service.image;

import com.pleaseWorkNow.hope.dto.ImageDto;
import com.pleaseWorkNow.hope.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<ImageDto> addImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
    void deleteImageById(Long id);

}
