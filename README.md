## About Architecture
![System Architecture](assets/ecom-architecture.png?raw=true)

1. **API Gateway**: Serves as the single entry point for all client requests & handles authentication and authorization using **Keycloak**.
2. **Customer Service**: Handles operations related to customer management, such as registration, and profile updates.
3. **Product Service**: Manages product information, including details like price, available quantity, and descriptions.
4. **Order Service**: Manages orders placed by customers. It interacts with the Customer Service to validate customer details and the Product Service to confirm product availability. It asynchronously sends payment confirmation to the Notification Service via Kafka
5. **Payment Service**: Handles payment processing for orders. It asynchronously sends payment confirmation to the Notification Service via Kafka.
6. **Notification Service**: Consume messages from Kafka (order and payment confirmations) and sends email to customers.
7. **Discovery Service** (Eureka Server): It allows microservices to find and communicate with each other dynamically.
8. **Config Server**: centralizes configuration management, enabling services to fetch their configurations at runtime from a version-controlled repository.
