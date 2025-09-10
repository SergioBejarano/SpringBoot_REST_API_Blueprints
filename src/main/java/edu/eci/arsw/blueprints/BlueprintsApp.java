package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BlueprintsApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bs = ac.getBean(BlueprintsServices.class);

        // Crear y registrar planos
        Blueprint bp1 = new Blueprint("Sergio", "Casa",
                new Point[] { new Point(0, 0), new Point(1, 1), new Point(1, 1), new Point(2, 2) });
        Blueprint bp2 = new Blueprint("Sergio", "Parque",
                new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3) });
        bs.addNewBlueprint(bp1);
        bs.addNewBlueprint(bp2);

        // Consultar todos los planos
        System.out.println("Todos los planos:");
        for (Blueprint bp : bs.getAllBlueprints()) {
            System.out.println(bp.getName() + ": " + pointsToString(bp));
        }

        // Consultar planos por autor
        System.out.println("\nPlanos de Sergio:");
        for (Blueprint bp : bs.getBlueprintsByAuthor("Sergio")) {
            System.out.println(bp.getName() + ": " + pointsToString(bp));
        }

        // Consultar plano específico
        System.out.println("\nPlano específico Casa:");
        Blueprint casa = bs.getBlueprint("Sergio", "Casa");
        System.out.println(casa.getName() + ": " + pointsToString(casa));
    }

    private static String pointsToString(Blueprint bp) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < bp.getPoints().size(); i++) {
            Point p = bp.getPoints().get(i);
            sb.append("(" + p.getX() + "," + p.getY() + ")");
            if (i < bp.getPoints().size() - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
