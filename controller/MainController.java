package controller;

import model.AreaType;
import model.Emergency;
import model.Organisation;

import model.SeverityType;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Крава on 06.12.2015.
 */
public class MainController {
    protected List<Emergency> emergencies = new ArrayList<Emergency>();
    protected List<Organisation> organisations = new ArrayList<Organisation>();
    protected List<SeverityType> severities = new ArrayList<SeverityType>();
    private List<AreaType> areaTypes = new ArrayList<AreaType>();

    public MainController() {
        for (int i = 0; i < 5; i++) {
            areaTypes.add(new AreaType(i, "type #" + i, i * 12.75));
            severities.add(new SeverityType(i, "Blya Fuck" + i));
        }
    }

    public List<Emergency> getEmergencies() {
        return emergencies;
    }

    public List<AreaType> getAreaTypes() {
        return areaTypes;
    }

    public List<SeverityType> getSeverities() {
        return severities;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }
}
