package com.vaadin.tutorial.crm.ui.view.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.tutorial.crm.backend.service.CompanyService;
import com.vaadin.tutorial.crm.backend.service.ContactService;

public class DashboardView extends VerticalLayout {
    private CompanyService companyService;
    private ContactService contactService;

    public DashboardView(CompanyService companyService, ContactService contactService){
        this.companyService = companyService;
        this.contactService = contactService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getContactStats(){
        Span stats = new Span(contactService.count() + " contacts");
        stats.addClassName("contact-stats");
        return stats;
    }


}
