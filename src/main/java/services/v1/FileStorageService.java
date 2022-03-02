#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.v1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ${package}.config.FileStorageConfig;
import ${package}.exception.FileStorageException;
import ${package}.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		fileStorageLocation = Paths
				.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();
		
		try {
			Files.createDirectories(fileStorageLocation);
		}catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the upload files will be stored!", e);
		}
	}	
	
	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		}catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}
	
	public Resource loadFilesAsResource(String fileName) {
		try {
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}
			throw new MyFileNotFoundException("File not found " + fileName);
			
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
	}
	
}
