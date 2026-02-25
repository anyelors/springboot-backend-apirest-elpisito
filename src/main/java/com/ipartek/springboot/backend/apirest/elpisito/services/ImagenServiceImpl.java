package com.ipartek.springboot.backend.apirest.elpisito.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.springboot.backend.apirest.elpisito.dtos.ImagenDTO;
import com.ipartek.springboot.backend.apirest.elpisito.entities.Imagen;
import com.ipartek.springboot.backend.apirest.elpisito.enumerators.EntidadImagen;
import com.ipartek.springboot.backend.apirest.elpisito.exceptions.FileStorageException;
import com.ipartek.springboot.backend.apirest.elpisito.exceptions.ResourceNotFoundException;
import com.ipartek.springboot.backend.apirest.elpisito.mappers.ImagenMapper;
import com.ipartek.springboot.backend.apirest.elpisito.repositories.ImagenRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImagenServiceImpl {

	@Autowired
	private ImagenRepository imagenRepository;

	@Autowired
	private ImagenMapper imagenMapper;

	// Con esta propiedad estamos indicando que la ruta definida en "media.location"
	// que está en el archivo "application-properties" se refiere a la ruta de la
	// carpeta en la que queremos albergar nuestros archivos físicos.
	// En nuestro caso "C:/mediafiles".
	// @Value: sirve para dar un valor determinado a un atributo
	@Value("${media.location}")
	private String mediaPath;

	private final Tika tika = new Tika();

	// SUBIR IMAGEN IMAGEN FÍSICA Y GUARDAR REGISTRO

	public ImagenDTO store(MultipartFile file, EntidadImagen entidadImagen, Long entidadId, String alt) {

		// https://developer.mozilla.org/es/docs/Web/HTTP/Guides/MIME_types/Common_types
		// El tipo MIME nos dice inequívocamente que tipo de archivo es
		// Esta comprobación NO DEBEMOS hacerla con la extensión del archivo ".jpg"
		// etc...
		/* image/gif, image/png, image/jpeg, image/bmp, image/webp */

		// Lo primero que vamos a hacer es comprobar si el file está vacío
		if (file.isEmpty()) {

			throw new FileStorageException("Error al subir el archivo. No es posible almacenar un archivo vacío");

		}

		// Detectamos el tipo MIME real con TIKA

		String mimeType = null;

		try {

			mimeType = tika.detect(file.getInputStream());

		} catch (IOException e) {

			throw new FileStorageException("Error al subir el archivo. No ha sido posible analizar el archivo");

		}

		// Mapear extensiones que vamos a permitir (jpg, png y webp)

		String extension = switch (mimeType) {

		case "image/jpeg" -> "jpg";
		case "image/png" -> "png";
		case "image/webp" -> "webp";
		default -> throw new FileStorageException("Error al subir el archivo. Formato " + mimeType + "no permitido");

		};

		// Generar nombre único

		String nombreArchivo = UUID.randomUUID() + "." + extension;

		// Crear carpetas si no existen

		try {

			Path directorio = Paths.get(mediaPath, entidadImagen.name().toLowerCase(), entidadId.toString());
			Files.createDirectories(directorio);// el metodo crea los directorios SI NO EXISTEN

			Path ruta = directorio.resolve(nombreArchivo);// resolve añade el nombre del archivo al Path directorio
			Files.copy(file.getInputStream(), ruta); // el archivo está físicamente en el servidor

		} catch (IOException e) {

			throw new FileStorageException("Error al subir el archivo");

		}

		// Ahora guardamos la imagen en al BBDD

		Imagen img = new Imagen();
		img.setNombre(nombreArchivo);
		img.setEntidadImagen(entidadImagen);
		img.setEntidadId(entidadId);
		img.setAltImagen(alt);

		Imagen imagen = imagenRepository.save(img);

		return imagenMapper.toDto(imagen);

	}

	// VISUALIZAR IMAGEN

	public ResponseEntity<Resource> renderizarImagen(EntidadImagen entidadImagen, Long entidadId, String nombre) {

		try {

			Path path = Paths.get(mediaPath, entidadImagen.name().toLowerCase(), entidadId.toString(), nombre);

			// Si la imagen no existe físicamente no puedo devolverla
			if (!Files.exists(path)) {

				throw new ResourceNotFoundException("Archivo no encontrado");

			}

			// En este punto estamos seguros de que la imagen existe...

			Resource resource = new UrlResource(path.toUri());

			String mimeType = Files.probeContentType(path);

			if (mimeType == null) {

				mimeType = "application/octet-stream"; // Cualquier tipo de datos binarios
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(resource);

		} catch (MalformedURLException e) {

			throw new FileStorageException("Ruta invalida");

		} catch (IOException e) {

			throw new FileStorageException("No se pudo leer el archivo");
		}

	}

	// ELIMINAR IMAGEN (FISICA Y REGISTRO EN BBDD)
	public ImagenDTO deleteImagen(Long id) {
		// Ontenemos el objeto de la BBDD
		Imagen imagen = imagenRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La imagen con id " + id + " no existe"));

		// Eliminacion BBDD
		ImagenDTO imagenDTO = deleteById(id);

		// Creamos un objeto path
		Path path = Paths.get(mediaPath, imagen.getEntidadImagen().name().toLowerCase(),
				imagen.getEntidadId().toString(), imagen.getNombre());

		try {
			// Eliminacion fisica
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new FileStorageException("No se ha podido eliminar la imagen fisica");
		}

		return imagenDTO;
	}

	// OBTENER IMÁGENES POR ENTIDAD (BULK)
	// Este método es universal para todas las entidades que tienen imagen
	// (representadas
	// en el enumerador EntidadImagen).
	// Con una sola llamada a la BBDD vamos a obtener un Map con el id de la entidad
	// (Inmueble,Banner,BannerCarousel,Inmobiliaria...cualquier entidad que tenga
	// imágen o imágenes
	// y su correspondiente listado de imágenes
	// Entrada: entidad --> por ejemplo EntidadImagen.INMUEBLE
	// ids --> lista de ids de inmueble, banner, banner carousel, inmobiliaria...
	// Salida: un Map donde cada id de EntidadImagen tiene su propia lista de
	// ImagenDTO
	// Objetivo: Sacar todas las imágenes de todos los ids en una sola query y
	// evitar
	// (n+1)queries ---> EFICIENCIA
	// Se invoca desde los servicios de las entidades (entidades que tienen imagen)
	// por ejemplo desde InmuebleServiceImpl ( findAllBulk())

	public Map<Long, List<ImagenDTO>> getImagenesPorEntidadBulk(EntidadImagen entidad, List<Long> ids) {

		// Conseguimos las imágenes de la entidad
		List<Imagen> imagenes = imagenRepository.findByEntidadImagenAndEntidadIdIn(entidad, ids);

		// Conseguimos la imagenDTO de la entidad
		List<ImagenDTO> imagenesDTO = imagenMapper.toDtoList(imagenes);

		return imagenesDTO.stream().collect(Collectors.groupingBy(ImagenDTO::entidadId)); // Va creando un List de
																							// ImagenDTO por cada
																							// entidadId. ImagenDTO es
																							// un Record...por lo tanto
																							// no es getId() sino id()
		// Cada imagen lleva asociada una entidadId que es a la que corresponde. De ese
		// modo Collectors.groupingBy consigue crear el Map relacionando
		// cada imagen con su entidadId y agrupándolas)

	}

	// OBTENER IMÁGENES
	public List<ImagenDTO> getImagenes(EntidadImagen tipoEntidad, Long entidadId) {

		return imagenRepository.findByEntidadImagenAndEntidadId(tipoEntidad, entidadId).stream()
				.map(i -> imagenMapper.toDto(i)).toList();

	}

	public ImagenDTO save(Imagen t) {

		return imagenMapper.toDto(imagenRepository.save(t));

	}

	public ImagenDTO findById(Long id) {

		Imagen imagen = imagenRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La imagen con id " + id + " no existe"));
		return imagenMapper.toDto(imagen);

	}

	public ImagenDTO deleteById(Long id) {
		Imagen imagen = imagenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				"La imagen con id " + id + " que estás intentando eliminar no existe"));

		imagenRepository.deleteById(id);// DataAccessException (EmptyResultDataAccessException que hereda de
										// DataAccessException)

		return imagenMapper.toDto(imagen);
	}

}
