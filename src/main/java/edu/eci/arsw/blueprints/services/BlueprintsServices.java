/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp = null;

    @Autowired
    @Qualifier("redundancyFilter")
    BlueprintFilter filter = null;

    public void addNewBlueprint(Blueprint bp) throws edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> blueprints = bpp.getAllBlueprints();
        Set<Blueprint> filtered = new LinkedHashSet<>();
        for (Blueprint bp : blueprints) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint blueprint = bpp.getBlueprint(author, name);
        if (blueprint == null) {
            throw new BlueprintNotFoundException("Blueprint not found");
        }
        return filter.filter(blueprint);
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
        if (blueprints == null || blueprints.isEmpty()) {
            throw new BlueprintNotFoundException("Blueprints not found by author");
        }
        Set<Blueprint> filtered = new LinkedHashSet<>();
        for (Blueprint bp : blueprints) {
            filtered.add(filter.filter(bp));
        }
        return filtered;
    }
}
