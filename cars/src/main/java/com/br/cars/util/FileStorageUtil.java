package com.br.cars.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileStorageUtil {
    private static final List<String> IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");
    private static final String VIDEO_TYPE = "video/mp4";
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final long MAX_VIDEO_SIZE = 10 * 1024 * 1024; // 10MB

    public static void validateImage(MultipartFile file) throws IOException {
        if (!IMAGE_TYPES.contains(file.getContentType())) {
            throw new IOException("Tipo de arquivo de imagem não suportado");
        }

        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new IOException("Tamanho da imagem excede o limite de 5MB");
        }
    }

    public static void validateVideo(MultipartFile file) throws IOException {
        if (!VIDEO_TYPE.equals(file.getContentType())) {
            throw new IOException("Tipo de arquivo de vídeo não suportado. Use MP4");
        }

        if (file.getSize() > MAX_VIDEO_SIZE) {
            throw new IOException("Tamanho do vídeo excede o limite de 10MB");
        }
    }
}