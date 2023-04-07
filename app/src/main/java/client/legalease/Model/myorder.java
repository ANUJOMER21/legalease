package client.legalease.Model;

import java.util.ArrayList;

public class myorder {
 private String status;
 Orderslist OrderslistObject;


 // Getter Methods 

 public String getStatus() {
  return status;
 }

 public Orderslist getOrderslist() {
  return OrderslistObject;
 }

 // Setter Methods 

 public void setStatus(String status) {
  this.status = status;
 }

 public void setOrderslist(Orderslist orderslistObject) {
  this.OrderslistObject = orderslistObject;
 }
}
