// ------------------------------------------------------------
// Clase principal de arranque del proyecto Spring Boot
// ------------------------------------------------------------
// La anotación @SpringBootApplication indica a Spring que esta es
// la clase principal desde donde inicia toda la aplicación.
//
// El parámetro scanBasePackages se usa para especificar de manera
// explícita qué paquetes deben ser escaneados por Spring Boot para
// detectar automáticamente componentes (@Controller, @Service,
// @Repository, @RestController, @ControllerAdvice, etc.).
//
// En este caso, se incluyen los paquetes:
//  - "co.manuelerazo.tesis": contiene la lógica principal del proyecto.
//  - "co.manuelerazo.advice": contiene el manejador global de excepciones
//    (GlobalExceptionHandler), que se ubica fuera del paquete base principal.
// Esto garantiza que Spring pueda detectar y aplicar el manejo global
// de errores personalizado en toda la aplicación.
// ------------------------------------------------------------
package co.manuelerazo.tesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"co.manuelerazo.tesis",
	"co.manuelerazo.advice"
})
public class TesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesisApplication.class, args);
	}

}
