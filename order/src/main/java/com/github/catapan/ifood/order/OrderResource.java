package com.github.catapan.ifood.order;

import com.github.catapan.ifood.order.localization.Localization;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.util.List;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router) {
        router.route("/localizacoes*").handler(localizacaoHandler());
    }

    private SockJSHandler localizacaoHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permitted = new PermittedOptions();
        permitted.setAddress("novaLocalizacao");

        //Alterado na versoa 1.9
        //        BridgeOptions bridgeOptions = new BridgeOptions().addOutboundPermitted(permitted);
        //        handler.bridge(bridgeOptions);

        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permitted);
        handler.bridge(bridgeOptions);
        return handler;
    }

    @GET
    public List<PanacheMongoEntityBase> hello() {
        return Order.listAll();
    }

    @POST
    @Path("{idOrder}/localizacao")
    public Order novaLocalizacao(@PathParam("idOrder") String idOrder, Localization localization) {
        Order order = Order.findById(new ObjectId(idOrder));

        order.localizationDeliveryman = localization;
        String json = JsonbBuilder.create().toJson(localization);
        eventBus.sendAndForget("novaLocalizacao", json);
        order.persistOrUpdate();
        return order;
    }
}