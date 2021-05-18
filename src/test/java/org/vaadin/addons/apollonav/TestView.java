package org.vaadin.addons.apollonav;

import java.util.Arrays;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

/**
 * Test View for our {@link ApolloNav} add-on class. This class and others in
 * the test folder will not be included in the final JAR.
 */
@Route("")
public class TestView extends Div {

    public TestView() {
        getElement().getStyle().set("padding", "20px");
        ApolloNav nav = new ApolloNav();
        nav.setLabel("Main menu");
        nav.setItems(
                new ApolloNavItem("dashboard", "Dashboard", "lineBarChart"),
                new ApolloNavItem("customers", "Customers", "suitcase"),
                new ApolloNavItem("leads", "Leads", "tabA", null,
                        Arrays.asList(
                                new ApolloNavItem("leads/high-profile",
                                        "High profile", "userStar"),
                                new ApolloNavItem("leads/others", "Others",
                                        "userClock"))),
                new ApolloNavItem("todos", "To-dos", "tasks", 9, Arrays.asList(
                        new ApolloNavItem("leads/High", "High", null, 2),
                        new ApolloNavItem("leads/medium", "Medium", null, 4),
                        new ApolloNavItem("leads/low", "Low", null, 3))),
                new ApolloNavItem("inbounds", "Inbounds", "envelope", 12));
        nav.setWidth("200px");
        add(nav);
    }
}
