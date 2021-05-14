package com.jensjansson.vaadinnav;

import java.util.Arrays;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;

/**
 * Test View for our {@link VaadinNav} add-on class. This class and others in
 * the test folder will not be included in the final JAR.
 */
@Route("")
public class TestView extends Div {

    public TestView() {

        add(new Paragraph("hello"));
        getElement().getStyle().set("padding-left", "20px");
        VaadinNav nav = new VaadinNav();
        nav.setLabel("Main menu");
        nav.setItems(
                new VaadinNavItem("dashboard", "Dashboard", "lineBarChart"),
                new VaadinNavItem("customers", "Customers", "suitcase"),
                new VaadinNavItem("leads", "Leads", "tabA", null,
                        Arrays.asList(
                                new VaadinNavItem("leads/high-profile",
                                        "High profile", "userStar"),
                                new VaadinNavItem("leads/others", "Others",
                                        "userClock"))),
                new VaadinNavItem("todos", "To-dos", "tasks", 9, Arrays.asList(
                        new VaadinNavItem("leads/High", "High", null, 2),
                        new VaadinNavItem("leads/medium", "Medium", null, 4),
                        new VaadinNavItem("leads/low", "Low", null, 3))),
                new VaadinNavItem("inbounds", "Inbounds", "envelope", 12));
        nav.setWidth("200px");
        add(nav);
    }
}
