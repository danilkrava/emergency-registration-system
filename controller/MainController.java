package controller;

import model.AreaType;
import model.Emergency;
import model.Organisation;
import model.Severity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Крава on 06.12.2015.
 */
public class MainController {
    protected List<Emergency> emergencies = new ArrayList<Emergency>();
    protected List<Organisation> organisations = new ArrayList<Organisation>();
    protected List<Severity> severities = new ArrayList<Severity>();
    private List<AreaType> areaTypes = new ArrayList<AreaType>();

    public static void main(String[] args) {
        MainController cont = new MainController();
        for (int i = 0; i < 5; i++) {
            cont.areaTypes.add(new AreaType(i, "type #"+i, i*12.75));
            cont.severities.add(new Severity(i, "Blya Fuck"+i));

        }
    }
}
