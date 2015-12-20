package controller;

import model.*;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created by Крава on 06.12.2015.
 */
public class MainController {
    protected List<Emergency> emergencies = new ArrayList<Emergency>();
    protected List<Organisation> organisations = new ArrayList<Organisation>();
    protected List<SeverityType> severities = new ArrayList<SeverityType>();
    private List<AreaType> areaTypes = new ArrayList<AreaType>();
    private List<Region> regions = new ArrayList<Region>();

    public MainController() {
        for (int i = 0; i < 5; i++) {
            areaTypes.add(new AreaType(i, "type #" + i, i * 12.75));
            severities.add(new SeverityType(i, "Blya Fuck" + i));
            regions.add(new Region(i, "Region #"+i));
            organisations.add(new Organisation("Organisation #"+i, "Address #"+i, regions.get(i)));
            emergencies.add(new Emergency(new Date(2000+i, i, i), i, areaTypes.get(i), severities.get(i), organisations.get(i)));
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
