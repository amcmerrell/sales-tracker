import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("customers", Customer.all());
    model.put("hardware", Hardware.all());
    model.put("clothing", Clothing.all());
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/products/new", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    String productName = request.queryParams("product-name");
    String productDescription = request.queryParams("product-description");
    int productPrice = Integer.parseInt(request.queryParams("product-price"));
    String productType = request.queryParams("product-type");
    if (productType.equals("hardware")) {
      Hardware hardwareItem = new Hardware(productName, productDescription, productPrice);
      hardwareItem.save();
    } else {
      Clothing clothingItem = new Clothing(productName, productDescription, productPrice);
      clothingItem.save();
    }
    response.redirect("/");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/new", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    String customerName = request.queryParams("customer-name");
    String customerEmail = request.queryParams("customer-email");
    String customerAddress = request.queryParams("customer-address");
    Customer newCustomer = new Customer(customerName, customerEmail, customerAddress);
    newCustomer.save();
    response.redirect("/");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();

    model.put("template", "templates/admin.vtl");
    return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
