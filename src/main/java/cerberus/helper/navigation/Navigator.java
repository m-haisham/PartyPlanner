package cerberus.helper.navigation;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

/**
 * a navigator for {@link TabPane}
 */
public class Navigator extends Stack<Tab> {

    private TabPane pane;

    /**
     * default constructor
     * @param pane tabpane execute the functions
     */
    public Navigator(TabPane pane) {
        this.pane = pane;
    }

    /**
     * pop and select last pushed pane
     * @return last pushed pane
     */
    @Override
    public synchronized Tab pop() {
        Tab pop = super.pop();
        pane.getSelectionModel().select(pop);
        return pop;
    }

    /**
     * pushes the current tab to the stack
     * @param tab selects this
     * @return {@param tab}
     */
    @Override
    public Tab push(Tab tab) {
        Tab push = super.push(pane.getSelectionModel().getSelectedItem());
        pane.getSelectionModel().select(tab);
        return push;
    }
}
