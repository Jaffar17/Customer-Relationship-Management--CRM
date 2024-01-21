package com.vaadin.tutorial.crm.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Contact;

@Route("")
public class MainView extends VerticalLayout {
    private Grid<Contact> grid = new Grid<>(Contact.class);

    public MainView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(grid);
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email", "status");
    }
}