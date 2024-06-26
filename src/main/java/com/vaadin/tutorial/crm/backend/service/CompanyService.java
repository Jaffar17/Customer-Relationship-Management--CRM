package com.vaadin.tutorial.crm.backend.service;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CompanyService {
  private CompanyRepository companyRepository;
  public CompanyService(CompanyRepository companyRepository) {
  this.companyRepository = companyRepository;
  }
  public List<Company> findAll() {
  return companyRepository.findAll();
  }
  public Map<String, Integer> getEmployeeStatsPerCompany() {
    Map<String, Integer> employeePerCompanyStats = new HashMap<>();
    List<Company> companies = findAll();

    for(Company company: companies){
      employeePerCompanyStats.put(company.getName(), company.getEmployees().size());
    }

    return employeePerCompanyStats;
  }
}