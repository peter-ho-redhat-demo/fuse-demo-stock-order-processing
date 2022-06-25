package com.redhat.fuse;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.apache.camel.dataformat.bindy.fixed.BindyFixedLengthDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Continuously capture data from MQ
        from("amqp:queue:testing-queue")

            // Convert MQ message into a Java Fixed Length POJO
            .unmarshal(new BindyFixedLengthDataFormat(StockOrder.class))

            // Filter unwanted country stock order
            .choice()
                
                .when(simple("${body.instrumentCountryCode} == 'JP'"))
                    
                    .log(LoggingLevel.WARN, "Captured a Japan (JP) stock order. Filter and ignore it.")
                
                .otherwise()

                    // Give random award points to the Order by calling RESTful API
                    .enrich("direct:generate-random-award-points", new AggregationStrategy() {
                        @Override
                        public Exchange aggregate(Exchange originalExchange, Exchange httpExchange) {
                            StockOrder incomingOrder = originalExchange.getIn().getBody(StockOrder.class);
                            
                            incomingOrder.setRandomAwardPoints(Integer.parseInt(
                                httpExchange.getIn().getBody(String.class).trim()
                            ));
                            originalExchange.getIn().setBody(incomingOrder);
                            
                            return originalExchange;
                        }
                    })

                    // Access MySQL database to lookup client membership level
                    .enrich("direct:lookup-client-membership", new AggregationStrategy() {
                        @Override
                        public Exchange aggregate(Exchange originalExchange, Exchange sqlExchange) {
                            // Extract the client membership string
                            List<Map<String, Object>> queryResultRows = sqlExchange.getIn().getBody(List.class);
                            String queriedClientMembership = queryResultRows.get(0).get("clientMembership").toString();

                            // Put the queried membership data into the message
                            StockOrder incomingOrder = originalExchange.getIn().getBody(StockOrder.class);
                            incomingOrder.setClientMembership(queriedClientMembership);
                            originalExchange.getIn().setBody(incomingOrder);

                            return originalExchange;
                        }
                    })

                    // Convert enriched data into JSON string
                    .marshal().json(JsonLibrary.Jackson)

                    // And then send it to the destination REST HTTP server
                    .to("http:localhost:8083");


        // Here is about the steps to get the random award points 
        from("direct:generate-random-award-points")
            .setBody().simple("")
            .setHeader(Exchange.HTTP_QUERY, simple("?num=1&min=1&max=9999&col=1&base=10&format=plain&rnd=new"))
            .to("https://www.random.org/integers/");

        // Here is about the steps to lookup client membership
        from("direct:lookup-client-membership")
            .to("sql:SELECT * FROM customer_info WHERE clientNr=:#${body.clientNr}");

    }

    // Define where to connect when making an AMQP connection by Apache Camel
    @Bean
    AMQPConnectionDetails securedAmqpConnection() {
        return new AMQPConnectionDetails(
            "XXXXX",
            "XXXXX", 
            "XXXXX"
        );
    }
    
}