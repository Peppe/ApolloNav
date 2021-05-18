package org.vaadin.addons.apollonav.apollo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * One menu item to be used within a `ApolloNav`.
 */
public class ApolloNavItem implements Serializable {
    private String path;
    private String title;
    private String icon;
    private Integer badge;
    private List<ApolloNavItem> children = Collections.emptyList();

    public ApolloNavItem() {
    }

    public ApolloNavItem(String path, String title) {
        this(path, title, null, null, null);
    }

    public ApolloNavItem(String path, String title, String icon) {
        this(path, title, icon, null, null);
    }

    public ApolloNavItem(String path, String title, String icon,
            Integer badge) {
        this(path, title, icon, badge, null);
    }

    public ApolloNavItem(String path, String title, String icon, Integer badge,
            Collection<ApolloNavItem> children) {
        this.path = path;
        this.title = title;
        this.icon = icon;
        this.badge = badge;
        setChildren(children);
    }

    public String getPath() {
        return path;
    }

    /**
     * Set where the app should navigate to after the nav item is clicked. The
     * value is a string, like "about", and the route with the same path will be
     * navigated to.
     * 
     * @param path
     * @return
     */
    public ApolloNavItem setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTitle() {
        return title;
    }

    /**
     * The caption for the menu item as shown in the menu, for example "About
     * the app".
     * 
     * @param title
     * @return
     */
    public ApolloNavItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    /**
     * An icon for the menu item. These are Vaadin Icons. The name is the name
     * of the icon in camelCase, like "commentEllipsis".
     * 
     * Check https://vaadin.com/components/vaadin-icons/html-examples to see
     * which icons are available. Check the file `vaadin-icons-bundle.js` within
     * the add-on for exact names for each icon.
     * 
     * @param icon
     * @return
     */
    public ApolloNavItem setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getBadge() {
        return badge;
    }

    /**
     * Set a number badge next to row, to indicate that there is x new things in
     * the view.
     * 
     * @param badge
     * @return
     */
    public ApolloNavItem setBadge(Integer badge) {
        this.badge = badge;
        return this;
    }

    public List<ApolloNavItem> getChildren() {
        return children;
    }

    /**
     * Set children to a menu item. The menu item will get a chevron that the
     * user can open to see the additional views. The children are
     * `ApolloNavItem`s as well.
     * 
     * @param children
     * @return
     */
    public ApolloNavItem setChildren(ApolloNavItem... children) {
        return setChildren(Arrays.asList(children));
    }

    /**
     * Set children to a menu item. The menu item will get a chevron that the
     * user can open to see the additional views. The children are
     * `ApolloNavItem`s as well.
     *
     * @param children
     * @return
     */
    public ApolloNavItem setChildren(Collection<ApolloNavItem> children) {
        this.children = children != null ? new ArrayList<>(children)
                : Collections.emptyList();
        return this;
    }
}
