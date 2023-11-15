import java.util.Scanner;

public class preguntas {

	public static void main(String[] args) {
		
		Scanner sc;
		sc=new Scanner(System.in);
		String res ;
		 
		
		 String [] preguntas = {
			    "1. ¿Cuál de los siguientes derechos es un derecho fundamental en el ámbito laboral?\na) Derecho a la huelga.\nb) Bono de rendimiento.",
			    "2. ¿Cuál es el período de preaviso mínimo que un empleado debe dar a su empleador al renunciar?\na) 10 días.\nb) 15 días.",
			    "3. ¿Cuál es la duración semanal máxima de la jornada ordinaria laboral promedio anual?\na) 35 horas a la semana.\nb) 40 horas a la semana.",
			    "4. ¿Cuál es la duración mínima del período de descanso semanal?\na) 24 horas consecutivas.\nb) 36 horas consecutivas.",
			    "5. ¿Si un empleado trabaja horas extras, tiene que ser compensado de alguna manera?\na) Si, con una retribución salarial o con días de vacaciones.\nb) Si, con ventajas exclusivas en la empresa.",
			    "6. ¿Cuál de las siguientes afirmaciones es cierta sobre el período de prueba en un contrato laboral?\na) El período de prueba dependerá del tipo de contrato laboral.\nb) El período de prueba es igual para todos los contratos laborales.",
			    "7. ¿Cuál es el plazo para presentar una reclamación laboral en caso de despido injustificado?\na) 10 días hábiles.\nb) 20 días hábiles.",
			    "8. ¿Qué organismo supervisa el cumplimiento de las normas laborales y arbitra en disputas laborales?\na) El Ministerio de Hacienda y Administraciones Públicas.\nb) El Servicio de Empleo Estatal.",
			    "9. ¿Cuál de las siguientes acciones está protegida como derecho de los trabajadores?\na) Falta de pago de salarios a tiempo.\nb) Falta de puntualidad de un empleado.",
			    "10. ¿Quién está obligado a pagar las cotizaciones a la Seguridad Social?\na) Solo el empleado.\nb) Ambos, el empleado y el empleador.",
			    "11. ¿Cuál es la duración mínima del período de descanso diario obligatorio?\na) 9 horas.\nb) 11 horas.",
			    "12. ¿Cuánto tiempo de permiso de paternidad se otorga a los padres?\na) 4 semanas cuando nace el bebe y otras 10 semanas en cualquier momento.\nb) 6 semanas cuando nace el bebe y otras 10 semanas en cualquier momento.",
			    "13. ¿En qué situación un empleado tiene derecho a un permiso de lactancia?\na) Sólo si la madre no está disponible.\nb) Siempre que el empleado sea padre o madre.",
			    "14. ¿Cuál de las siguientes afirmaciones es cierta sobre el salario mínimo interprofesional (SMI)?\na) Varía según la experiencia laboral del empleado.\nb) Se establece anualmente y varía según la inflación y el costo de vida.",
			    "15. ¿Cuántos días de vacaciones pagadas tiene derecho un trabajador a tiempo completo después de un año de trabajo?\na) 20 días.\nb) 30 días.",
			    "16. ¿Cuál es la situación con respecto a la baja por enfermedad?\na) Los primeros días de la baja por enfermedad no están pagados, pero a partir del cuarto día se recibe un subsidio económico por parte de la Seguridad Social.\nb) La baja por enfermedad está completamente pagada desde el primer día.",
			    "17. ¿En qué circunstancias un empleador puede despedir a un empleado sin previo aviso ni indemnización?\na) En casos de mala conducta grave.\nb) No se puede despedir a un empleado sin previo aviso ni indemnización.",
			    "18. ¿Cuál es la edad mínima para trabajar, según la legislación laboral?\na) 14 años.\nb) 16 años.",
			    "19. ¿Qué organismo es responsable de administrar el sistema de Seguridad Social?\na) El Instituto Nacional de la Seguridad Social (INSS).\nb) El Ministerio de Sanidad, Consumo y Bienestar Social.",
			    "20. ¿En qué casos un empleado puede solicitar una excedencia voluntaria?\na) Sólo en caso de enfermedad grave.\nb) En cualquier momento, siempre que lo comunique con suficiente antelación.",
			    "21. ¿Que es un contrato temporal?\na) Un contrato de trabajo que tiene una duración específica y no puede ser terminado antes de su vencimiento.\nb) Un contrato de trabajo que se establece de manera indefinida y no tiene una fecha de finalización.",
			    "22. ¿Cuál es la duración máxima de un contrato de formación?\na) 2 años.\nb) 3 años.",
			    "23. ¿En qué circunstancias un empleado puede negarse a trabajar en horas extraordinarias?\na) Siempre que lo desee, sin necesidad de justificación.\nb) Si se supera el límite legal de horas extraordinarias anuales.",
			    "24. ¿Cuántos días de permiso por matrimonio se otorgan a los empleados?\na) 10 días.\nb) 15 días.",
			    "25. ¿Cuál es la duración máxima de un contrato de trabajo temporal?\na) 2 años.\nb) 3 años."
			};
		 
		 
		
				 
		 String[] respuestas = {"a", "b", "b", "b", "a", "a", "b", "b", "a", "b", "b", "b", "b", "b", "b", "a", "a", "b", "a", "b", "a", "b", "b", "b", "a"};
		 
		 
		 int aux= (int) (Math.random()*25+1);
		
System.out.println(preguntas[aux]);
	res= sc.next();
	
	if (res.equalsIgnoreCase(respuestas[aux])){
		System.out.println("Correcto");
		
	}else {
		System.out.println("Incorrecto");
	}
	
	}

}
