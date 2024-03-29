package com.vaadin.tutorial.crm.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.service.CompanyService;
import com.vaadin.tutorial.crm.backend.service.ContactService;


@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
    private ContactService contactService;
    private Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField filterText = new TextField();
    private ContactForm contactForm;

    public MainView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;

        addClassName("list-view");
        setSizeFull();

        configureFilter();
        configureGrid();

        contactForm = new ContactForm(companyService.findAll());
        contactForm.addListener(ContactForm.SaveEvent.class, this::saveContact);
        contactForm.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        contactForm.addListener(ContactForm.CloseEvent.class, e->closeEditor());

        Div content = new Div(grid, contactForm);
        content.addClassName("content");
        content.setSizeFull();

        add(filterText, content);

        updateList();

        closeEditor();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "status");

        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        grid.getColumns().forEach(columns -> columns.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private void updateList(){
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        // contactForm.setContact(null); TODO: remove this line, I think code works fine without this line
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void saveContact(ContactForm.SaveEvent event) {
        contactService.save(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        contactService.delete(event.getContact());
        updateList();
        closeEditor();
    }
}