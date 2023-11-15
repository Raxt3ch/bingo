import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JTextField;

public class Carton extends JFrame {
    private JPanel txtnombre;
    private JButton[][] nums = new JButton[3][9];
    private JButton btnbingo;
    private JButton btnlinea;
    private JButton btnSal;
    private static String nombrelin = "anonymous";
    private static PrintWriter writer;
    private JTextField textnombre;
    private List<Integer> uniqueNumbers;
    private CheckFileThread fileThread;
    private static Carton cartonInstance;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("\\\\192.168.0.29\\bingo\\estado.txt"));
            String estado = br.readLine();
            br.close();

            if ("true".equals(estado.trim())) {
                EventQueue.invokeLater(() -> {
                    try {
                        Carton frame = new Carton();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación");
                        e.printStackTrace();
                        System.exit(0);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(null, "El programa todavía no se ha iniciado");
                System.exit(0);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar al archivo '\\\\192.168.0.29\\bingo\\estado.txt'");
        }
    }

    public Carton() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1326, 469);
        txtnombre = new JPanel();
        txtnombre.setBackground(new Color(30, 144, 255));
        txtnombre.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(txtnombre);
        txtnombre.setLayout(null);

        int buttonCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                nums[i][j] = new JButton("");
                nums[i][j].setFont(new Font("Tahoma", Font.BOLD, 35));
                nums[i][j].setBounds(10 + j * 110, 56 + i * 101, 100, 90);
                txtnombre.add(nums[i][j]);
                buttonCount++;
            }
        }

        uniqueNumbers = llenarYOrdenarNumeros();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                nums[i][j].setText(uniqueNumbers.get(i * nums[i].length + j).toString());
            }
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                if (!nums[i][j].isEnabled()) {
                    nums[i][j].setText("");
                }
            }
        }

        JLabel lblNewLabel = new JLabel("Bingo ALMINGO !!!");
        lblNewLabel.setForeground(new Color(255, 250, 250));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(39, 11, 1233, 32);
        txtnombre.add(lblNewLabel);

        btnbingo = new JButton("Bingoo");
        btnbingo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnbingo.setBounds(1093, 58, 103, 77);
        txtnombre.add(btnbingo);

        btnlinea = new JButton("Linea");
        btnlinea.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnlinea.setBounds(1093, 146, 103, 77);
        txtnombre.add(btnlinea);

        btnSal = new JButton("SALIR");
        btnSal.setForeground(new Color(192, 192, 192));
        btnSal.setBackground(new Color(128, 0, 0));
        btnSal.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSal.setBounds(1169, 323, 103, 53);
        txtnombre.add(btnSal);

        JLabel lblNewLabel_1 = new JLabel("Bienvenido");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(10, 11, 72, 14);
        txtnombre.add(lblNewLabel_1);

        textnombre = new JTextField();
        textnombre.setEditable(false);
        textnombre.setFont(new Font("Tahoma", Font.BOLD, 14));
        textnombre.setBackground(new Color(0, 128, 255));
        textnombre.setBounds(78, 7, 244, 20);
        txtnombre.add(textnombre);
        textnombre.setColumns(10);

        nombrelin = JOptionPane.showInputDialog("Pon el nombre del jugador");

        if (nombrelin == null || nombrelin.trim().isEmpty()) {
            nombrelin = "anonymous" + (int) (Math.random() * 1000 + 1);
        }
        textnombre.setText(nombrelin);

        eventos();

        fileThread = new CheckFileThread(this);
        fileThread.start();
    }

    private List<Integer> llenarYOrdenarNumeros() {
        int[][] carton = new int[3][9];
        ArrayList<Integer> numerosGenerados = new ArrayList<>();
        List<Integer> uniqueNumbers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int disabledCount = 0; // Counter for disabled buttons in the row
            for (int j = 0; j < 9; j++) {
                int n;
                do {
                    switch (j) {
                        case 0:
                            n = aleatorio(1, 9);
                            break;
                        case 8:
                            n = aleatorio(80, 90);
                            break;
                        default:
                            n = aleatorio(10 * j, (10 * j) + 9);
                            break;
                    }
                } while (numerosGenerados.contains(n));

                numerosGenerados.add(n);
                carton[i][j] = n;
                uniqueNumbers.add(n);

                // Controlling enabled/disabled buttons in the row
                if (disabledCount < 4 && Math.random() < 0.5) {
                    nums[i][j].setEnabled(false);
                    nums[i][j].setText(""); // Set text to empty for disabled buttons
                    disabledCount++;
                } else {
                    nums[i][j].setEnabled(true);
                    nums[i][j].setText(Integer.toString(n)); // Set text for enabled buttons
                }
            }
        }

        // Ensuring column restrictions
        for (int j = 0; j < 9; j++) {
            int disabledCount = 0;
            int rowIndexDisabled = -1;
            for (int i = 0; i < 3; i++) {
                if (!nums[i][j].isEnabled()) {
                    disabledCount++;
                    rowIndexDisabled = i;
                }
            }

            // Checking column restrictions
            if (disabledCount > 2 || disabledCount == 0) {
                if (rowIndexDisabled != -1) {
                    nums[rowIndexDisabled][j].setEnabled(false);
                    nums[rowIndexDisabled][j].setText(""); // Set text to empty for column-disabled buttons
                }
            }
        }

        return uniqueNumbers;
    }

    
    private int aleatorio(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    
	private void eventos() {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                nums[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        if (clickedButton.isEnabled()) {
                            clickedButton.setEnabled(false);
                            int clickedNumber = Integer.parseInt(clickedButton.getText());
                            uniqueNumbers.remove(Integer.valueOf(clickedNumber));
                        }
                    }
                });
            }
        }

        btnSal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(Carton.this, "¿Estás seguro que quieres salir?", "Aviso",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        btnlinea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
            	
            	
            	
            	boolean comlin = complinea(nums);
   

                if (comlin) {
                    System.out.println("Hay linea");
                    linea()
;                    
                } else {
                    JOptionPane.showMessageDialog(Carton.this, "No hay linea");
                }
            }
        });

        btnbingo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean combin = false;
                combin = compbingo();

                if (combin) {
                	System.out.println("Hay bingo");
                    bingo();
                } else {
                    JOptionPane.showMessageDialog(Carton.this, "NO Hay bingo intentalo de nuevo");
                }
            }
        });
    }

	protected boolean complinea(JButton[][] bingoBoard) {
	    String fileName = "\\\\192.168.0.29\\bingo\\numeros.txt";
	    List<Set<String>> lines = new ArrayList<>();

	    // Initialize sets for each line
	    for (int i = 0; i < bingoBoard.length; i++) {
	        lines.add(new HashSet<String>());
	    }

	    try {
	        File file = new File(fileName);
	        Scanner scanner = new Scanner(file);

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] numbersInFile = line.trim().split("\\s+");

	            // Add numbers from the file to sets for each line
	            for (int i = 0; i < bingoBoard.length; i++) {
	                Collections.addAll(lines.get(i), numbersInFile);
	            }
	        }
	        scanner.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return false; // Error reading file
	    }

	    int completeLinesCount = 0;

	    // Validate each line in the bingo board
	    for (int i = 0; i < bingoBoard.length; i++) {
	        Set<String> currentLineNumbers = new HashSet<>();
	        for (JButton button : bingoBoard[i]) {
	            if (!button.getText().isEmpty()) {
	                currentLineNumbers.add(button.getText());
	            }
	        }

	        // Check if all numbers in the current line are in the corresponding set from the file
	        if (lines.get(i).containsAll(currentLineNumbers)) {
	            completeLinesCount++;
	        }
	    }

	    return completeLinesCount > 0; // Return true if at least one line is complete
	}


	protected boolean compbingo() {
	    String fileName = "\\\\192.168.0.29\\bingo\\numeros.txt";
	    Set<String> allNumbers = new HashSet<>();

	    // Lee todos los números del archivo y los agrega a un conjunto
	    try {
	        File file = new File(fileName);
	        Scanner scanner = new Scanner(file);

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] numbersInFile = line.trim().split("\\s+");
	            Collections.addAll(allNumbers, numbersInFile);
	        }
	        scanner.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return false; // Error al leer el archivo
	    }

	    // Verifica si todos los números mostrados están presentes en el conjunto de números del archivo
	    for (JButton[] row : nums) {
	        for (JButton button : row) {
	            String buttonText = button.getText();
	            if (!buttonText.isEmpty() && !allNumbers.contains(buttonText)) {
	                return false; // Encontró un número que no está presente en el archivo
	            }
	        }
	    }

	    return true; // Todos los números mostrados están en el archivo
	}


	public static void bingo() {
	    try {
	        boolean haybin = false;
	        int cont = 0;

	        while (true) {
	            haybin = preguntabin(); // Aquí deberías tener una función para preguntar si hay bingo
	            cont++;

	            if (haybin) {
	                try (PrintWriter writerBl = new PrintWriter("\\\\192.168.0.29\\bingo\\bl.txt");
	                     PrintWriter writerNomlin = new PrintWriter("\\\\192.168.0.29\\bingo\\nombin.txt")) {

	                    writerBl.println("bingo");
	                    writerNomlin.println(nombrelin);

	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                }
	                break;
	            }

	            if (cont == 3) {
	                JOptionPane.showMessageDialog(cartonInstance, "Has superado el límite de preguntas");
	                System.exit(0);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static void linea() {
	    try {
	        int cont = 0;
	        boolean haylin = false; 

	        while (true) { 
	            haylin = preguntalin();
	            cont++;

	            if (haylin) { 
		            try (PrintWriter writerBl = new PrintWriter("\\\\192.168.0.29\\bingo\\bl.txt");
			                 PrintWriter writerNomlin = new PrintWriter("\\\\192.168.0.29\\bingo\\nomlin.txt")) {

			                writerBl.println("linea");
			                writerNomlin.println(nombrelin);
			                
			            } catch (FileNotFoundException e) {
			                e.printStackTrace();
			            }
		            break;
		            
	            }
	            if (cont == 3) {
		            JOptionPane.showMessageDialog(cartonInstance, "Has superado el limite de preguntas");
		            System.exit(0);
		        }
	         
	        }

	       

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    class CheckFileThread extends Thread {
        private volatile boolean running = true;
        private Carton carton;

        public CheckFileThread(Carton carton) {
            this.carton = carton;
        }

        public void stopThread() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(500); // Wait for 0.5 seconds

                    BufferedReader br = new BufferedReader(new FileReader("\\\\192.168.0.29\\bingo\\bl.txt"));
                    String content = br.readLine();
                    br.close();

                    if (content != null) {
                        switch (content) {
                            case "bingo":
                                carton.ayBingo(); // Call method for Bingo
                                break;
                            case "linea":
                                carton.ayLinea(); // Call method for Linea
                                break;
                            default:
                                // Do nothing or handle other cases
                                break;
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ayBingo() {
        String nomlin = ""; // Variable para guardar el contenido del archivo

        try {
            BufferedReader br = new BufferedReader(new FileReader("\\\\192.168.0.29\\bingo\\nombin.txt"));
            nomlin = br.readLine(); // Lee la primera línea del archivo
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(Carton.this, "Hay bingo!!  El ganador es: " + nomlin);
        System.exit(0);

        try {
            PrintWriter writer = new PrintWriter("\\\\192.168.0.29\\bingo\\bl.txt", "UTF-8");
            writer.print("null"); // Escribe "null" en el archivo
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * 
	 */
	public void ayLinea() {
	    String nomlin = ""; // Variable para guardar el contenido del archivo

	    try {
	        BufferedReader br = new BufferedReader(new FileReader("\\\\192.168.0.29\\bingo\\nomlin.txt"));
	        nomlin = br.readLine(); // Lee la primera línea del archivo
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    JOptionPane.showMessageDialog(Carton.this, "Hay linea por parte del: " + nomlin);
	    btnlinea.setEnabled(false);

	    try {
	        PrintWriter writer = new PrintWriter("\\\\192.168.0.29\\bingo\\bl.txt", "UTF-8");
	        writer.print("null"); // Escribe "null" en el archivo
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
	
	
	public static boolean preguntalin(){

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
		

	res = JOptionPane.showInputDialog(preguntas[aux]);
	
	if (res.equalsIgnoreCase(respuestas[aux])){
		JOptionPane.showMessageDialog(cartonInstance, "Correcto tiene linea");
		return true;
		
	}else {
		JOptionPane.showMessageDialog(cartonInstance, "Incrroecto preuba de nuevo");
		return false;
	}
		
	}
    
	
	
	public static boolean preguntabin(){

		String res ;
		
		String[] preguntas = {
			    "¿Cuál es el plazo para notificar un embarazo al empleador?\n" +
			    "a) 15 días después de la fecha prevista de parto.\n" +
			    "b) 7 días antes de la fecha prevista de parto.\n" +
			    "c) No hay un plazo establecido.",

			    "¿Cuál es la duración máxima de un contrato de interinidad?\n" +
			    "a) 3 meses.\n" +
			    "b) 6 meses.\n" +
			    "c) 12 meses.",

			    "¿Cuál es el salario mínimo interprofesional (SMI) para el año 2023?\n" +
			    "a) 950 euros al mes.\n" +
			    "b) 1.080 euros al mes.\n" +
			    "c) 1.200 euros al mes.",

			    "¿Cuál es el plazo para notificar una situación de incapacidad temporal (IT) a la empresa?\n" +
			    "a) 3 días.\n" +
			    "b) 7 días.\n" +
			    "c) 14 días.",

			    "¿Qué organismo se encarga de regular las relaciones laborales colectivas?\n" +
			    "a) El Ministerio de Trabajo y Economía Social.\n" +
			    "b) El Instituto Nacional de la Seguridad Social (INSS).\n" +
			    "c) El Servicio Público de Empleo Estatal (SEPE).",

			    "¿Qué es la Inspección de Trabajo y Seguridad Social?\n" +
			    "a) Una entidad encargada de contratar trabajadores temporales.\n" +
			    "b) Un organismo que supervisa y garantiza el cumplimiento de las normas laborales.\n" +
			    "c) Una organización sindical en España.",

			    "¿Cuál es la duración mínima de un contrato de trabajo?\n" +
			    "a) 1 mes.\n" +
			    "b) 3 meses.\n" +
			    "c) Puede variar según el tipo de contrato y las circunstancias específicas.",

			    "¿Qué derechos tienen los empleados a tiempo parcial en comparación con los empleados a tiempo completo?\n" +
			    "a) Los empleados a tiempo parcial tienen los mismos derechos que los empleados a tiempo completo en todos los aspectos.\n" +
			    "b) Los empleados a tiempo parcial tienen los mismos derechos en cuanto a salario y beneficios, pero pueden tener diferencias en términos de días de vacaciones y permisos.\n" +
			    "c) Los empleados a tiempo parcial tienen más derechos que los empleados a tiempo completo en términos de flexibilidad laboral y horas de trabajo.",

			    "¿Cuál es el plazo para solicitar una excedencia por cuidado de un familiar?\n" +
			    "a) 5 días antes de necesitarla.\n" +
			    "b) 10 días antes de necesitarla.\n" +
			    "c) No hay un plazo fijo, pero debe notificarse con antelación.",

			    "¿Cuál es la duración máxima de un contrato de obra o servicio?\n" +
			    "a) 1 año.\n" +
			    "b) 2 años.\n" +
			    "c) 3 años.",

			    "¿En qué circunstancias un empleado puede ejercer el derecho de huelga?\n" +
			    "a) En cualquier momento, sin restricciones.\n" +
			    "b) Sólo si su empleador no cumple con el convenio colectivo.\n" +
			    "c) En situaciones de conflicto laboral grave.",

			    "¿Qué plazo tiene un empleado para solicitar la reincorporación a su puesto de trabajo después de una excedencia?\n" +
			    "a) 15 días antes de finalizar la excedencia.\n" +
			    "b) Dentro del mes siguiente al cese de la causa que lo produjo.\n" +
			    "c) No tiene derecho a reincorporarse.",

			    "¿Cuál es el plazo para notificar un cambio de puesto de trabajo?\n" +
			    "a) 7 días.\n" +
			    "b) 15 días.\n" +
			    "c) 30 días.",

			    "¿Cuál es la duración mínima de un contrato de relevo?\n" +
			    "a) 2 años.\n" +
			    "b) 3 años.\n" +
			    "c) El tiempo que le falte al trabajador que ha solicitado la jubilación anticipada para alcanzar la edad de jubilación ordinaria que corresponda.",

			    "¿En qué situaciones un empleado tiene derecho a una reducción de jornada laboral?\n" +
			    "a) Siempre que lo solicite.\n" +
			    "b) Para el cuidado de un hijo menor de 12 años con discapacidad.\n" +
			    "c) Sólo durante el período de prueba.",

			    "¿Cuándo es ilegal una concatenación de contratos temporales?\n" +
			    "a) Cuando dos contratos temporales sumen más de 24 meses en un periodo de 30 meses.\n" +
			    "b) Cuando dos contratos temporales sumen más de 20 meses en un periodo de 30 meses.\n" +
			    "c) Cuando dos contratos temporales sumen más de 28 meses en un periodo de 30 meses.",

			    "¿Cuál es el plazo para notificar una situación de riesgo durante el embarazo?\n" +
			    "a) Al inicio del embarazo.\n" +
			    "b) Inmediatamente después de conocer la situación de riesgo.\n" +
			    "c) No hay un plazo fijo, pero debe notificarse con antelación.",

			    "¿Cuál es la duración máxima de un contrato de trabajo de duración determinada por circunstancias de la producción?\n" +
			    "a) 3 meses.\n" +
			    "b) 6 meses.\n" +
			    "c) 1 año.",

			    "¿En qué situaciones un empleado puede ser despedido de manera procedente?\n" +
			    "a) Por cualquier motivo que el empleador considere adecuado.\n" +
			    "b) Solo por causas objetivas, disciplinarias o económicas.\n" +
			    "c) Siempre que el empleado tenga un contrato.",

			    "¿Qué es el contrato de relevo?\n" +
			    "a) Un contrato a tiempo parcial.\n" +
			    "b) Un contrato que se utiliza para sustituir a un trabajador que ha solicitado la jubilación parcial.\n" +
			    "c) Un contrato que se utiliza para sustituir temporalmente a un empleado en situación de incapacidad.",

			    "¿Cuál es el plazo mínimo para notificar un permiso de mudanza?\n" +
			    "a) No hay que notificarlo.\n" +
			    "b) 15 días.\n" +
			    "c) 30 días.",

			    "¿Qué es el permiso por enfermedad?\n" +
			    "a) Un período de tiempo en el que a un empleado ausente no se le paga su salario completo.\n" +
			    "b) Un beneficio laboral en el que se establece un tiempo de descanso para que los trabajadores se dediquen al cuidado de su salud y seguridad en caso de enfermedad sin dejar de recibir compensación.\n" +
			    "c) Un programa de seguro médico proporcionado por el empleador.",

			    "¿Cuál es el período de preaviso mínimo que un empleador debe dar a un empleado antes de un despido objetivo?\n" +
			    "a) 15 días.\n" +
			    "b) 30 días.\n" +
			    "c) 60 días.",

			    "¿En qué casos un empleado tiene derecho a un permiso por fallecimiento de un familiar?\n" +
			    "a) Por fallecimiento del cónyuge, pareja de hecho o familiar dentro del primer grado de consanguinidad o afinidad.\n" +
			    "b) Sólo si el empleado es el único responsable del funeral.\n" +
			    "c) En cualquier caso de fallecimiento de un familiar, sin importar el parentesco.",

			    "¿Cuál es la duración máxima de un contrato de prácticas?\n" +
			    "a) 6 meses.\n" +
			    "b) 1 año.\n" +
			    "c) 2 años."
			};
		
		
		
		String[] respuestas = {"c", "c", "b", "a", "a", "b", "c", "b", "c", "c", "c", "b", "b", "c", "b", "a", "b", "c", "b", "b", "b", "b", "a", "a", "c"};

		
		 int aux= (int) (Math.random()*25+1);
			

	res = JOptionPane.showInputDialog(preguntas[aux]);
	
	if (res.equalsIgnoreCase(respuestas[aux])){
		JOptionPane.showMessageDialog(cartonInstance, "Correcto tienes bingo");
		return true;
		
	}else {
		JOptionPane.showMessageDialog(cartonInstance, "Incorrecto prueba de nuevo");
		return false;
	}
	}
	
	
}
