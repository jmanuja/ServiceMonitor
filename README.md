# Service Monitor

This monitor will be used to monitor the status of multiple services.  A service is defined as a host/port combination.  

The monitor will allow callers to register interest in a service, and a polling frequency. The callers will be notified when the service goes up and down.

At any time a service can be configured with a planned service outage. The service outage will specify a start and end time for which no notifications for that service will be delivered.

The monitor will allow callers to define a grace time.  If a service is not responding, the monitor will wait for the grace time before notifying any clients.  If the service goes back on line during this grace time, no notification will be sent.  

# Technical Details

IDE : Netbeans IDE

Type of Project : Java Swing 

Design Pattern : Publisher - Subscriber Pattern & Solid Principles are used.
