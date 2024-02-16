package jsv.unededucaanalisis.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jsv.unededucaanalisis.modelo.TareaRevisor;
import jsv.unededucaanalisis.servicios.PersonaService;
import jsv.unededucaanalisis.servicios.TareaRevisorService;

@Controller
public class RevisionController {
	
	@Autowired
	private TareaRevisorService servicio;
	
	@Autowired
	private PersonaService servicioPersona;	

	Integer[][] matrizRevisores;
	Integer[][] matrizAleatoria;
	Integer[][] matrizProducto;
	float utilidadTotal;
	
	// Muestra las tareas para que el usuario seleccione una de ellas
	@GetMapping("/seleccionTareaRevision")
	public String revisores(Model model)
	{		
		List<TareaRevisor> listaTareasRevisor = servicio.findAll();
		model.addAttribute("listaTareasRevisor", listaTareasRevisor);

		return "selectTareaRevision";
	}
	
	// Seleccionará una tarea de Revisión para generar después la matriz
	@PostMapping("/seleccionadaTareaRevision")
	public String seleccionadodRevisor(Model model, @RequestParam("tareaRevisor") Integer id, @RequestParam("numIteraciones") Integer iteraciones)
	{	
		int nodos = servicioPersona.findAll().size();
		Integer mR[][] = new Integer[nodos+1][nodos+1];
		Integer mP[][] = new Integer[nodos+1][nodos+1];
		int uT = 0;
		
		matrizAleatoria = generarMatrizAleatoria(nodos);	    
		utilidadTotal = 0;		
		for(int k= 0; k < iteraciones; k++) {
			mR = generarMatrizRevisores(nodos, servicio.findById(id).getNumEstudiantes()); 
			mP = generarMatrizProducto(nodos, mR, matrizAleatoria);
	    
		    for (int i = 1; i < nodos+1; i++) 
		        for (int j = 1; j < nodos+1; j++) 
		        	uT = uT + mP[i][j]; 
		    
		    // Utilidad total será la suma de todos los elementos de la matriz producto
		    // dividido entre el número de estudiantes por el número de nodos, así obtenemos
		    // un valor entre 0 y 100.
		    uT = uT / (nodos*servicio.findById(id).getNumEstudiantes());	
		    
	    
		    if(utilidadTotal < uT) {
		    	matrizRevisores = mR;
		    	matrizProducto = mP;
		    	utilidadTotal = uT;
		    }
		}
		
	    return "redirect:/generaMatriz";
	}
	
	// Muestra las tareas para que el usuario seleccione una de ellas
	@GetMapping("/generaMatriz")
	public String generaMatriz(Model model)
	{		
		model.addAttribute("matriz", matrizRevisores);
		model.addAttribute("matrizAleatorios", matrizAleatoria);
		model.addAttribute("matrizProducto", matrizProducto);
		model.addAttribute("utilidadTotal", utilidadTotal);
		return "listMatriz";
	}

    // Método para llenar la matriz con números aleatorios 0 o 1, asegurándose de que haya el mismo número de 1 en cada fila y columna
    // y que en la diagonal principal no pueda haber 1
    private Integer[][] generarMatrizRevisores(int nodos, int revisores) {
        Integer[][] matriz = new Integer[nodos+1][nodos+1];
        int valor;
        Random random = new Random(System.currentTimeMillis());
        List<Integer> randomList = new ArrayList<Integer>();
        int[] vectorSuma = new int[nodos];
        
        int intentos = 0;
        boolean exito = false;
        boolean fracaso = false;       
        
        // Doy valor a la primera fila y a la primera columna con el Id de cada alumno
	    for (int i = 1; i < nodos+1; i++) { 
	    	matriz[0][i] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[i][0] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[0][0] = null;
	    }
	           
        while(intentos < 20) {            
        	// Inicializa vectorSuma, que llevará la cuenta de cuantos revisores hay ya en cada columna
            for (int i = 1; i < nodos+1; i++) {
            	vectorSuma[i-1] = 0;
            	for(int j = 1; j < nodos+1; j++ )
            		matriz[i][j] = 0;
            }
            int contador = 0;
            fracaso = false;        
            
        	// La idea es ir introduciendo un revisor (marcado con un 1) en cada iteración.
	        // Se hará por filas, en la primera fila se mete un revisor, en la segunda fila
	        // otro y así n veces. Volverá de nuevo a la fila 1 y meterá un segundo revisor
	        // procediendo de la misma forma        
	        for (int i = 1; i <= revisores; i++) {

	        	// En cada fila, verá qué huecos hay libres y de entre esos, escogerá uno de forma aleatoria
	        	for (int j = 1; j < nodos+1 ; j++) {
	        		for(int k = 1; k < nodos+1 ; k++) {
	        			if((k!=j) && vectorSuma[k-1]<revisores && matriz[j][k]==0)
	        				randomList.add(k);        				
	        		}
	        		if (randomList.size() > 0) {
	        			// Toma un valor aleatorio de la lista de posible posiciones de esa fila
		        		valor = random.nextInt(randomList.size());
		        		contador = contador + 1;		
		        		matriz[j][randomList.get(valor)] = 1;
		        		vectorSuma[randomList.get(valor)-1] = vectorSuma[randomList.get(valor)-1] + 1;
		        		randomList.clear();
		        		if (i == revisores) exito = true;
	        		}
	        		else {
	        			intentos = intentos + 1;
		        		randomList.clear();
		        		fracaso = true;
		        		exito = false;
	        			break;
	        		}       				
	            }
	        	if(fracaso) { 
	        		break;} 
	        }if(exito) {
	        	break; 
	        	}	
        }
        return matriz;    
    }
    
    // Método para generar una matriz de nxn donde n será igual al número de alumnos
    // y cuyos valores estarán comprendidos entre 1 y 100 (valores enteros)
    private Integer[][] generarMatrizAleatoria(int nodos) {
    	Integer[][] matriz = new Integer[nodos+1][nodos+1];
    	Random numAleatorio;
    	
    	numAleatorio= new Random(System.currentTimeMillis());
    	
	    for (int i = 1; i < nodos+1; i++) { 
	    	matriz[0][i] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[i][0] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[0][0] = null;
	    }
	    
        for (int i = 1; i < nodos+1; i++) {
        	for(int j = 1; j < nodos+1; j++ )        {		
    			//Generar número aleatorio int entre 1 y 100
        		if(i!=j)
        			matriz[i][j] = 1+numAleatorio.nextInt(100);  
        		else 
        			matriz[i][j] = 0;
        	}
        }
        
		return matriz;
    }
    
    // Método para generar una matriz de nxn donde n será igual al número de alumnos
    // y cuyos valores serán los productos de las celdas con la misma posición
    private Integer[][] generarMatrizProducto(int nodos, Integer[][] m1, Integer[][] m2) {
    	Integer[][] matriz = new Integer[nodos+1][nodos+1];

	    for (int i = 1; i < nodos+1; i++) { 
	    	matriz[0][i] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[i][0] = servicioPersona.findAll().get(i-1).getId() ;
	    	matriz[0][0] = (Integer) null;
	    }
	    
        for (int i = 1; i < nodos+1; i++) {
        	for(int j = 1; j < nodos+1; j++ )         		
        		matriz[i][j] = m1[i][j] * m2[i][j];
        }
        
		return matriz;
    }
    
}
