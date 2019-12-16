package cerberus.helper.navigation;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class Navigator extends Stack<Tab> {

    private TabPane pane;

    public Navigator(TabPane pane) {
        this.pane = pane;
    }

    @Override
    public synchronized Tab pop() {
        Tab pop = super.pop();
        pane.getSelectionModel().select(pop);
        return pop;
    }

    @Override
    public Tab push(Tab tab) {
        Tab push = super.push(tab);
        return push;
    }

    public Tab push(Tab current, Tab next) {
        push(current);
        pane.getSelectionModel().select(next);
        return next;
    }
}
