package com.jensjansson.vaadinnav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

@Tag("vaadin-nav")
@JsModule("./vaadin-nav/vaadin-nav.ts")
/**
 * A navigation component meant for e.g. main menu. Supports links, captions,
 * icons, nesting and a number badge. Perfect for setting within the drawer of
 * `AppLayout`.
 * 
 * Use setLabel(String) to put the label above the component. Use
 * setItems(VaadinNavItem...) put configure the menu items.
 * 
 * Here's a full example that you can use as a starting point ``` VaadinNav nav
 * = new VaadinNav("Main menu"); nav.setItems( new VaadinNavItem("dashboard",
 * "Dashboard", "lineBarChart"), new VaadinNavItem("customers", "Customers",
 * "suitcase"), new VaadinNavItem("leads", "Leads", "tabA", null, Arrays.asList(
 * new VaadinNavItem("leads/high-profile", "High profile", "userStar"), new
 * VaadinNavItem("leads/others", "Others", "userClock"))), new
 * VaadinNavItem("todos", "To-dos", "tasks", 9, Arrays.asList( new
 * VaadinNavItem("leads/High", "High", null, 2), new
 * VaadinNavItem("leads/medium", "Medium", null, 4), new
 * VaadinNavItem("leads/low", "Low", null, 3))), new VaadinNavItem("inbounds",
 * "Inbounds", "envelope", 12)); nav.setWidth("200px"); add(nav); ```
 */
public class VaadinNav extends LitTemplate implements HasSize, HasStyle {

    List<VaadinNavItem> items = Collections.emptyList();
    private boolean pendingUpdate = false;

    /**
     * Initializes an empty navigation component. Nothing will be shown if you
     * don't call `setLabel` or `setItems`. See the class JavaDocs for a full
     * example.
     */
    public VaadinNav() {
    }

    /**
     * Initializes an navigation component with a set caption. See the class
     * JavaDoc for a full example.
     */
    public VaadinNav(String label) {
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
     * example, and check `VaadinNavItem` for available API.
     * 
     * @param items
     */
    public void setItems(VaadinNavItem... items) {
        setItems(Arrays.asList(items));
    }

    /**
     * Set the items that should be shown in the menu. See class JavaDoc for an
     * example, and check `VaadinNavItem` for available API.
     *
     * @param items
     */
    public void setItems(Collection<VaadinNavItem> items) {
        this.items = new ArrayList<>(items);
        setClientItems();
    }

    /**
     * Get current menu items
     * 
     * @return current menu items
     */
    public List<VaadinNavItem> getItems() {
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

    private JsonArray createItemsJsonArray(Collection<VaadinNavItem> items) {
        JsonArray jsonItems = Json.createArray();
        for (VaadinNavItem item : items) {
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
