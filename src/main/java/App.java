import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";
    staticFileLocation("/public");

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("customers", Customer.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("clothing", Clothing.all());
      model.put("hardware", Hardware.all());
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/admin/reports", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("all-transactions", Transaction.all());
      model.put("monthly-transactions",Transaction.findMonthlyTransactions());
      model.put("quarterly-transactions",Transaction.findQuarterlyTransactions());
      model.put("Transaction", Transaction.class);
      model.put("template", "templates/reports.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/products/new", (request, response) -> {
      String productName = request.queryParams("product-name");
      String productDescription = request.queryParams("product-description");
      int productPrice = Integer.parseInt(request.queryParams("product-price"));
      String type = request.queryParams("product-type");
      if (type.equals("hardware")) {
        Hardware hardwareItem = new Hardware(productName, productDescription, productPrice);
        hardwareItem.save();
      } else {
        Clothing clothingItem = new Clothing(productName, productDescription, productPrice);
        clothingItem.save();
      }
      response.redirect("/admin");
      return null;
    });

    post("/users/new", (request, response) -> {
      String customerName = request.queryParams("customer-name");
      String customerEmail = request.queryParams("customer-email");
      String customerAddress = request.queryParams("customer-address");
      Customer newCustomer = new Customer(customerName, customerEmail, customerAddress);
      newCustomer.save();
      response.redirect("/users/" + newCustomer.getId());
      return null;
    });

    post("/users/login", (request, response) -> {
      String userId = request.queryParams("customer-id");
      response.redirect("/users/" + userId + "");
      return null;
    });

    get("/users/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("customer", Customer.find(Integer.parseInt(request.params(":id"))));
      model.put("hardware", Hardware.all());
      model.put("clothing", Clothing.all());
      model.put("template", "templates/customer-products.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:customer_id/products/:product_id/purchase", (request, response) -> {
      int customerId = Integer.parseInt(request.params("customer_id"));
      int productId = Integer.parseInt(request.params("product_id"));
      String type = Product.getType(productId);
      int salePrice;
      Product someProduct = null;
      if (type.equals("hardware")) {
        someProduct = Hardware.find(productId);
        salePrice = someProduct.getPrice();
      } else {
        someProduct = Clothing.find(productId);
        salePrice = someProduct.getPrice();
      }
      try{
        someProduct.depleteInventory(1);
      }
      catch(UnsupportedOperationException e){
        request.session().attribute("message", e.getMessage());
        response.redirect("/users/" + customerId + "/products/" + productId + "/outofstock");
      }
      Transaction newTransaction = new Transaction(productId, customerId);
      newTransaction.save();
      response.redirect("/users/" + customerId + "/products/" + productId + "/transactions/" + newTransaction.getId());
      return null;
    });

    get("/users/:customer_id/products/:product_id/outofstock", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int customerId = Integer.parseInt(request.params("customer_id"));
      model.put("id", customerId);
      model.put("message", request.session().attribute("message"));
      model.put("template", "templates/out-of-stock.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/:customer_id/products/:product_id/transactions/:transaction_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("customer", Customer.find(Integer.parseInt(request.params(":customer_id"))));
      int productId = Integer.parseInt(request.params("product_id"));
      String type = Product.getType(productId);
      if (type.equals("hardware")) {
        model.put("product", Hardware.find(Integer.parseInt(request.params(":product_id"))));
      } else {
        model.put("product", Clothing.find(Integer.parseInt(request.params(":product_id"))));
      }
      model.put("transaction", Transaction.find(Integer.parseInt(request.params(":transaction_id"))));
      model.put("template", "templates/transaction-receipt.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/:customer_id/products/:product_id/delete", (request, response) -> {
      int productId = Integer.parseInt(request.params("product_id"));
      String type = Product.getType(productId);
      if (type.equals("hardware")) {
        Hardware.find(Integer.parseInt(request.params(":product_id"))).delete();
      } else {
        Clothing.find(Integer.parseInt(request.params(":product_id"))).delete();
      }
      response.redirect("/admin");
      return null;
    });

    post("/users/:customer_id/products/:product_id/restock", (request, response) -> {
      int productId = Integer.parseInt(request.params("product_id"));
      String type = Product.getType(productId);
      if (type.equals("hardware")) {
        Hardware.find(Integer.parseInt(request.params(":product_id"))).restock();
      } else {
        Clothing.find(Integer.parseInt(request.params(":product_id"))).restock();
      }
      response.redirect("/admin");
      return null;
    });
  }
}
