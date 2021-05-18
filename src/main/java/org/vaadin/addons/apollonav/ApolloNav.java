package org.vaadin.addons.apollonav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

@Tag("apollo-nav")
@JsModule("./apollo-nav/apollo-nav.ts")
/**
 * A navigation component meant for e.g. main menu. Supports links, captions,
 * icons, nesting and a number badge. Perfect for setting within the drawer of
 * `AppLayout`.
 * 
 * Use setLabel(String) to put the label above the component. Use
 * setItems(ApolloNavItem...) put configure the menu items.
 * 
 * Here's a full example that you can use as a starting point
 * 
 * <pre>
 * ApolloNav nav = new ApolloNav("Main menu");
 * nav.setItems(new ApolloNavItem("dashboard", "Dashboard", "lineBarChart"),
 *         new ApolloNavItem("customers", "Customers", "suitcase"),
 *         new ApolloNavItem("leads", "Leads", "tabA", null, Arrays.asList(
 *                 new ApolloNavItem("leads/high-profile", "High profile",
 *                         "userStar"),
 *                 new ApolloNavItem("leads/others", "Others", "userClock"))),
 *         new ApolloNavItem("todos", "To-dos", "tasks", 9,
 *                 Arrays.asList(
 *                         new ApolloNavItem("leads/High", "High", null, 2),
 *                         new ApolloNavItem("leads/medium", "Medium", null, 4),
 *                         new ApolloNavItem("leads/low", "Low", null, 3))),
 *         new ApolloNavItem("inbounds", "Inbounds", "envelope", 12));
 * nav.setWidth("200px");
 * add(nav);
 * </pre>
 */
public class ApolloNav extends Component implements HasSize, HasStyle {

    List<ApolloNavItem> items = Collections.emptyList();
    private boolean pendingUpdate = false;

    /**
     * Initializes an empty navigation component. Nothing will be shown if you
     * don't call `setLabel` or `setItems`. See the class JavaDocs for a full
     * example.
     */
    public ApolloNav() {
    }

    /**
     * Initializes an navigation component with a set caption. See the class
     * JavaDoc for a full example.
     */
    public ApolloNav(String label) {
        setLabel(label);
    }

    /**
     * Change the label of the component, displayed at the top of the component.
     * Use `null` if you don't want a label.
     * 
     * @param label
     */
    public void setLabel(String label) {
        getElement().setProperty("label", label);
    }

    /**
     * Get the current label
     * 
     * @return current label set on the component
     */
    public String getLabel() {
        return getElement().getProperty("label", null);
    }

    /**
     * Set the items that should be shown in the menu. See class JavaDoc for an
     * example, and check `ApolloNavItem` for available API.
     * 
     * @param items
     */
    public void setItems(ApolloNavItem... items) {
        setItems(Arrays.asList(items));
    }

    /**
     * Set the items that should be shown in the menu. See class JavaDoc for an
     * example, and check `ApolloNavItem` for available API.
     *
     * @param items
     */
    public void setItems(Collection<ApolloNavItem> items) {
        this.items = new ArrayList<>(items);
        setClientItems();
    }

    /**
     * Get current menu items
     * 
     * @return current menu items
     */
    public List<ApolloNavItem> getItems() {
        return items;
    }

    private void setClientItems() {
        if (!pendingUpdate) {
            pendingUpdate = true;
            getElement().getNode().runWhenAttached(
                    ui -> ui.beforeClientResponse(this, ctx -> {
                        getElement().setPropertyJson("items",
                                createItemsJsonArray(items));
                        pendingUpdate = false;
                    }));
        }
    }

    private JsonArray createItemsJsonArray(Collection<ApolloNavItem> items) {
        JsonArray jsonItems = Json.createArray();
        for (ApolloNavItem item : items) {
            JsonObject jsonItem = Json.createObject();
            if (item.getPath() != null) {
                jsonItem.put("path", item.getPath());
            }

            if (item.getTitle() != null) {
                jsonItem.put("title", item.getTitle());
            }

            if (item.getIcon() != null) {
                jsonItem.put("icon", item.getIcon());
            }

            if (item.getBadge() != null) {
                jsonItem.put("badge", item.getBadge());
            }

            if (item.getChildren() != null && !item.getChildren().isEmpty()) {
                JsonArray children = createItemsJsonArray(item.getChildren());
                jsonItem.put("children", children);
            }

            jsonItems.set(jsonItems.length(), jsonItem);
        }

        return jsonItems;
    }
}
