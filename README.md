# Navigation component for Vaadin

A navigation component meant for e.g. main menu. Supports links, captions,
icons, nesting and a number badge. Perfect for setting within the drawer
of `AppLayout`.

## Example usage

```java
ApolloNav nav=new ApolloNav();
        nav.setLabel("Main menu");
        nav.setItems(
        new ApolloNavItem("dashboard","Dashboard","lineBarChart"),
        new ApolloNavItem("customers","Customers","suitcase"),
        new ApolloNavItem("leads","Leads","tabA",null,
        Arrays.asList(
        new ApolloNavItem("leads/high-profile",
        "High profile","userStar"),
        new ApolloNavItem("leads/others","Others",
        "userClock"))),
        new ApolloNavItem("todos","To-dos","tasks",9,Arrays.asList(
        new ApolloNavItem("leads/High","High",null,2),
        new ApolloNavItem("leads/medium","Medium",null,4),
        new ApolloNavItem("leads/low","Low",null,3))),
        new ApolloNavItem("inbounds","Inbounds","envelope",12));
        nav.setWidth("200px");
```

## Development instructions

### Important Files

* TheAddon.java: this is the addon-on component class. You can add more classes
  if you wish, including other Components.
* TestView.java: A View class that let's you test the component you are
  building. This and other classes in the test folder will not be packaged
  during the build. You can add more test view classes in this package.
* assembly/: this folder includes configuration for packaging the project into a
  JAR so that it works well with other Vaadin projects and the Vaadin Directory.
  There is usually no need to modify these files, unless you need to add JAR
  manifest entries.

If you are using static resources such as images, JS (e.g. templates) and CSS
files the correct location for them is under
the `/src/main/resources/META-INF/resources/frontend` directory and is described
here [Resource Cheat Sheet](https://vaadin.com/docs/v14/flow/importing-dependencies/tutorial-ways-of-importing.html#resource-cheat-sheet)in
more details.

### Deployment

Starting the test/demo server:

```
mvn jetty:run
```

This deploys demo at http://localhost:8080

### Branching information

* `master` the latest version of the starter, using latest stable platform
  version
* `v14` the version for Vaadin 14, which is the newest LTS

## Publishing to Vaadin Directory

You should change the `organisation.name` property in `pom.xml` to your own
name/organization.

```
    <organization>
        <name>###author###</name>
    </organization>
```

You can create the zip package needed
for [Vaadin Directory](https://vaadin.com/directory/) using

```
mvn versions:set -DnewVersion=1.0.0 # You cannot publish snapshot versions 
mvn install -Pdirectory
```

The package is created as `target/{project-name}-1.0.0.zip`

For more information or to upload the package,
visit https://vaadin.com/directory/my-components?uploadNewComponent
