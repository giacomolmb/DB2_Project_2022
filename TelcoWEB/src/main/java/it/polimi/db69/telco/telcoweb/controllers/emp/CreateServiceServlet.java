package it.polimi.db69.telco.telcoweb.controllers.emp;

import it.polimi.db69.telco.telcoejb.entities.MobilePhone;
import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.entities.Service;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;
import it.polimi.db69.telco.telcoejb.services.ProductService;
import it.polimi.db69.telco.telcoejb.services.ServiceService;
import it.polimi.db69.telco.telcoweb.exceptions.InputException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateServiceServlet", value = "/createservice")
public class CreateServiceServlet extends HttpServlet {
    TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db69.telco.telcoejb.services/ServiceService")
    ServiceService serviceService;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/createservice.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceType = request.getParameter("servicetype");
        String minutes = request.getParameter("minutes");
        String minutesFee = request.getParameter("minutesfee");
        String sms = request.getParameter("sms");
        String smsFee = request.getParameter("smsfee");
        String giga = request.getParameter("giga");
        String gigaFee = request.getParameter("gigafee");

        System.out.println(serviceType);

        //todo validazione (sbatti)

        serviceService.createService(serviceType, Integer.parseInt(minutes), Integer.parseInt(sms), Double.parseDouble(minutesFee), Double.parseDouble(smsFee), Integer.parseInt(giga), Double.parseDouble(gigaFee));

        response.sendRedirect(getServletContext().getContextPath()+"/employeehomepage");
    }

    private void checkInputs(String productName, String fee) throws InputException{
        if(productName == null || productName.isEmpty()){
            throw new InputException("Invalid input! Please insert a product name");
        }

        if(fee == null || fee.isEmpty()){
            throw new InputException("Invalid input! Please insert a product monthly fee!");
        }

        try{
            Double.parseDouble(fee);
        } catch (NumberFormatException e){
            throw new InputException("Invalid input! Please insert a valid monthly fee!");
        }
    }
}
