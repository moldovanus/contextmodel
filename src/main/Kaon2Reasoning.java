package main;

import model.impl.ontologyImpl.OntologyModelFactory;
import org.semanticweb.kaon2.api.*;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 11:22:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Kaon2Reasoning {
    public void initializeOntology() {
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
        resolver.registerReplacement("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#", "file:" + OntologyModelFactory.ontologyFile);
        ontologyManager.setOntologyResolver(resolver);
        Ontology ontology;
        try {
            ontology = ontologyManager.createOntology("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#", new HashMap<String, Object>());
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String args[]) {
        Kaon2Reasoning kaon2Reasoning = new Kaon2Reasoning();
        kaon2Reasoning.initializeOntology();
    }
}
