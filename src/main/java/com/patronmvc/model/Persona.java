package com.patronmvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @NotNull(message = "El documento/código no puede estar vacío")
    @Min(value = 1000, message = "El documento de la persona debe tener más de 3 dígitos (mayor o igual a 1000)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 5, max = 80, message = "El nombre de la persona debe ser mayor a 5 dígitos y hasta 80 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @NotNull(message = "La edad no puede estar vacía")
    @Min(value = 1, message = "Edad no válida (debe ser mayor a 0)")
    @Max(value = 150, message = "Edad no válida")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "La profesión no puede estar vacía")
    @Size(min = 1, max = 120, message = "La profesión debe tener entre 1 y 120 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "La profesión solo puede contener letras y espacios")
    @Column(name = "profesion", nullable = false, length = 120)
    private String profesion;

    @NotNull(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono", nullable = false)
    private Long telefono;

    public Persona() {
    }

    public Persona(Integer id, String nombre, Integer edad, String profesion, Long telefono) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.profesion = profesion;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
}
