# Order 

## Endpoints
HTTP Method,Endpoint,Description,Service Method Called
POST,/orders,Create a new order,create(OrderDto)
GET,/orders/number/{orderNumber},Get order by order number,findOrderById(String)
PUT,/orders/{id},Update order by UUID,"update(UUID, OrderDto)"
DELETE,/orders/{id},Delete order by UUID,delete(UUID)
POST,/orders/{id}/cancel,Cancel order by UUID,cancel(UUID)
POST,/orders/{id}/complete,Complete order by UUID,complete(UUID)

### POSTMAN Test 
[] POST, /orders
[] GET, /orders/{id}
[] PUT, /orders/{id}
[] DELETE , /orders/{id}
[] POST, /orders/{id}/cancel
[] POST, /orders/{id}/complete


# OrderItems 



