package com.example.servicio_libros.service;

import com.example.servicio_libros.model.Libro;
import com.example.servicio_libros.repository.LibroRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository){
        this.libroRepository =libroRepository;

    }
    public List<Libro> listarLibro(){
        return libroRepository.findAll();


    }
    public Libro buscarPorId(@NonNull Long id){
        return libroRepository.findById(id.orElse(null));
    }
    public Libro guardarLibro(Libro libro){
        return libroRepository.save(libro);
    }
    public Libro actualizarLibro(Long id, Libro libro){
        Libro libroExistente = buscarPorId(id);

        if (libroExistente == null){
            return  null;
        }

        libroExistente.setTitulo(libro.getTitulo());
        libroExistente.setAutor((libro.getAutor()));
        libroExistente.setIsbn((libro.getIsbn()));
        libroExistente.setCategoria((Libro.getCategoria()));
        libroExistente.setPrecio(libro.getPrecio());
        libroExistente.setAnioPublicacion(libro.getAnioPublicacion());
        libroExistente.setEditorialId(libro.getEditorialId());
        libroExistente.setActivo(libro.getActivo());
        return libroRepository.save(libroExistente);

    }


}
