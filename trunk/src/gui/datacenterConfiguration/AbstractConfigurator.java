package gui.datacenterConfiguration;

import jade.core.Agent;
import model.impl.util.ModelAccess;
 

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: Jun 18, 2010
 * Time: 7:07:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConfigurator {
    protected Map<String, String> columnsToolTips;
    protected JScrollPane tablePane;
    protected JTable configurationTable;


    protected AbstractConfigurator( ) {

    }

    public abstract void createEntities(Agent agent);

    public abstract void insertEmptyRow();

    public abstract void duplicateSelectedRow();

    public abstract void removeRow();

    public abstract List<String[]> getTableData();

    public abstract void setTableData(List<String[]> data);

    public JScrollPane getTablePane() {
        return tablePane;
    }
}
