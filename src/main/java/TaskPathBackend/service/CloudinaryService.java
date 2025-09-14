package TaskPathBackend.service;


import TaskPathBackend.exception.CloudinaryException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private static final String DEFAULT_FOLDER = "usuarios";

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, Object> uploadFile(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", DEFAULT_FOLDER,
                    "resource_type", "auto" // permite imagen/video/pdf si quisieras
            ));
        } catch (IOException e) {
            throw new CloudinaryException("Error al subir archivo a Cloudinary", e);
        }
    }

    public void deleteFile(String publicId) {
        try {
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (!"ok".equals(result.get("result"))) {
                throw new CloudinaryException("No se pudo eliminar el archivo con id: " + publicId, null);
            }
        } catch (IOException e) {
            throw new CloudinaryException("Error al eliminar archivo de Cloudinary", e);
        }
    }
}
